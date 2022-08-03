package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.shop.controller.dto.OrderItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.OrdersDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderResultDTO
import com.lionTF.CShop.domain.shop.models.OrderStatus
import com.lionTF.CShop.domain.shop.models.orderItemDTOtoOrderItem
import com.lionTF.CShop.domain.shop.models.ordersDTOToOrders
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.lionTF.CShop.domain.shop.repository.MemberRepository
import com.lionTF.CShop.domain.shop.repository.OrderItemRepository
import com.lionTF.CShop.domain.shop.repository.OrderRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val itemRepository: ItemRepository,
    private val memberRepository: MemberRepository,
    private val orderItemRepository: OrderItemRepository
) {
    //상품 주문 메소드
    @Transactional
    fun requestOrder(requestOrderDTO: RequestOrderDTO) : RequestOrderResultDTO {
        var canOrder: Boolean = true
        var errorItemsId: MutableList<Long> = mutableListOf()

        //요청한 상품들 재고 검사
        for(info in requestOrderDTO.orderItems){
            val requestAmount = info.amount
            val existAmount = itemRepository.getReferenceById(info.itemId).amount

            // 요청한 주문 상품이 재고보다 많을 경우
            if(requestAmount > existAmount){
                canOrder = false
                errorItemsId.add(info.itemId)
            }
        }

        //모든 상품의 재고가 충분한 경우
        if(canOrder){
            val member = memberRepository.getReferenceById(requestOrderDTO.memberId)

            //order 저장
            val orders = orderRepository.save(
                ordersDTOToOrders(
                    OrdersDTO(
                        member,
                        OrderStatus.COMPLETE,
                        requestOrderDTO.orderAddress
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
                    orderItemDTOtoOrderItem(
                        OrderItemDTO(
                            orders,
                            item,
                            info.amount
                        )
                    )
                )
            }

            return RequestOrderResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "상품 주문을 성공하였습니다.",
                errorItems = errorItemsId
            )
        }
        else{
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "상품 재고가 부족하여 주문하지 못하였습니다.",
                errorItems = errorItemsId,
            )
        }
    }
}