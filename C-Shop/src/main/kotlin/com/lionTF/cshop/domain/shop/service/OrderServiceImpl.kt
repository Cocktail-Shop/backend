package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.repository.AdminOrderRepository
import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.models.OrderItem
import com.lionTF.cshop.domain.shop.models.OrderStatus
import com.lionTF.cshop.domain.shop.models.Orders
import com.lionTF.cshop.domain.shop.repository.ItemRepository
import com.lionTF.cshop.domain.shop.repository.MemberRepository
import com.lionTF.cshop.domain.shop.repository.OrderItemRepository
import com.lionTF.cshop.domain.shop.repository.OrderRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.OrderService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val adminOrderRepository: AdminOrderRepository,
    private val itemRepository: ItemRepository,
    private val memberRepository: MemberRepository,
    private val orderItemRepository: OrderItemRepository
) : OrderService{
    //상품 주문 메소드
    @Transactional
    override fun requestOrder(requestOrderDTO: RequestOrderDTO) : RequestOrderResultDTO {
        // var canOrder: Boolean = true
        var isNotDeleted: Boolean = true
        var isAmountEnough: Boolean = true
        var isPositive: Boolean = true
        var errorItemsId: MutableList<Long> = mutableListOf()

        //요청한 상품들 재고 검사
        for(info in requestOrderDTO.orderItems){
            val requestAmount = info.amount
            val existAmount = itemRepository.getReferenceById(info.itemId).amount

            //요청 수량이 양수인지 확인
            if(requestAmount <= 0){
                isPositive = false
                break
            }

            //삭제된 아이템인지 확인
            if(itemRepository.getReferenceById(info.itemId).itemStatus){
                // 요청한 주문 상품이 재고보다 많을 경우
                if(requestAmount > existAmount){
                    isAmountEnough = false
                    errorItemsId.add(info.itemId)
                }
            }
            else{
                isNotDeleted = false
                errorItemsId.add(info.itemId)
            }
        }

        if(!isPositive){
            return RequestOrderResultDTO.setNotPositiveError(errorItemsId)
        }

        //모든 상품의 재고가 충분한 경우
        if(isNotDeleted and isAmountEnough){
            val member = requestOrderDTO.memberId?.let { memberRepository.getReferenceById(it) }
            //order 저장
            val orders = orderRepository.save(
                Orders.fromOrdersDTO(
                    OrdersDTO(
                        member,
                        OrderStatus.COMPLETE,
                        requestOrderDTO.address,
                        requestOrderDTO.addressDetail
                    )
                )
            )

            //val orderId = orders.orderId
            for(info in requestOrderDTO.orderItems){
                val item = itemRepository.getReferenceById(info.itemId)
                //주문 수량만큼 재고 줄여주기
                item.amount -= info.amount
                itemRepository.save(item)

                //orderitem 저장
                orderItemRepository.save(
                    OrderItem.fromOrderItemDTO(
                        OrderItemDTO(
                            orders,
                            item,
                            info.amount
                        )
                    )
                )
            }

            return RequestOrderResultDTO.setRequestOrderSuccessResultDTO()
        }
        else{
            if(isNotDeleted){
                return RequestOrderResultDTO.setRequestOrderAmountFailResultDTO(errorItemsId)
            }
            else{
                return RequestOrderResultDTO.setRequestOrderStatusFailResultDTO(errorItemsId)
            }
        }
    }

    //주소 가져오기
    override fun getAddress(memberId: Long) : AddressDTO {
        return AddressDTO.fromMember(memberRepository.getReferenceById(memberId))
    }

    // 상품 삭제
    @Transactional
    override fun cancelOrder(orderId: Long): OrderResponseDTO {
        val existsOrder = orderRepository.existsById(orderId)

        return if (!existsOrder) {
            OrderResponseDTO.toFailDeleteItemResponseDTO()

        } else {
            val order = orderRepository.getReferenceById(orderId)
            order.cancelOrder()

            val orderItem = orderItemRepository.getOrderItemByOrdersId(orderId)
            orderItem.cancel()

            OrderResponseDTO.toSuccessDeleteItemResponseDTO()
        }
    }

    // 주문 조회
    override fun getShopOrders(pageable: Pageable): OrderResponseDTO {
        val findOrdersInfo = adminOrderRepository.findOrdersInfo(pageable)

        return if (findOrdersInfo.isEmpty) {
            OrderResponseDTO.toFailSearchShopOrdersDTO()
        } else {
            OrderResponseDTO.toSuccessSearchShopOrdersDTO()
        }
    }
}
