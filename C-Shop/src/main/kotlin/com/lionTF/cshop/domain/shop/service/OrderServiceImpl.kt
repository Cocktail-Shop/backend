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

        for(info in requestOrderDTO.orderItems){
            val requestAmount = info.amount
            val existAmount = itemRepository.getReferenceById(info.itemId).amount


            if(requestAmount <= 0){
                return RequestOrderResultDTO.setNotPositiveError()
            }

            if(itemRepository.getReferenceById(info.itemId).itemStatus){
                if(requestAmount > existAmount) return RequestOrderResultDTO.setRequestOrderAmountFailResultDTO()
            }
            else return RequestOrderResultDTO.setRequestOrderStatusFailResultDTO()
        }


        val member = requestOrderDTO.memberId?.let { memberRepository.getReferenceById(it) }

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

        for(info in requestOrderDTO.orderItems){
            val item = itemRepository.getReferenceById(info.itemId)
            item.amount -= info.amount
            itemRepository.save(item)

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
