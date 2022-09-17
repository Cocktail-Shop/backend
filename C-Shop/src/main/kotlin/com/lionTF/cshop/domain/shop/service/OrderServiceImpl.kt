package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.OrdersSearchDTO
import com.lionTF.cshop.domain.admin.repository.AdminOrderRepository
import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.models.DeliveryStatus
import com.lionTF.cshop.domain.shop.models.OrderItem
import com.lionTF.cshop.domain.shop.models.OrderStatus
import com.lionTF.cshop.domain.shop.models.Orders
import com.lionTF.cshop.domain.shop.models.QOrders.orders
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
) : OrderService {
    @Transactional
    override fun requestOrder(requestOrderDTO: RequestOrderDTO): Any {
        var zeroAmountCount = 0
        /**
         * 1.orderIds를 통해 전체 상품들 조회해옴(조회할때 수량 0인것, 삭제된것 제외하고 조회)
         * 2. 주문가능한 것 없으면 return
         * 3. requestAmount가  음수인지 확인(음수하나라도 있으면 주문 불가능)
         * 4. requestAmount가 0인지 확인(모두 0이면 주문 불가능)
         * 5. 주문
         * 전체 조회 (주문 가능한 것만, 수량0이상인것만 -> 사용자입력주문 검사 -> 주문 가능하면 return
         */
        println("주문시작")
        //내부적으로 잘못된 것 잡음
        val orderItemIds = requestOrderDTO.getOrderItemsIds()
        val orderItems = itemRepository.findAllByItemIdInAndItemStatusTrueAndAmountGreaterThan(orderItemIds) //다가져옴

        if (orderItems.isEmpty()) {
            return RequestOrderResultDTO.setRequestOrderStatusFailResultDTO()
        }

        val orderMap = orderItems.associateBy { it.itemId }


        requestOrderDTO.orderItems.filter { item ->
            val requestAmount = item.amount
            val existItem = orderMap[item.itemId] ?: throw NoSuchElementException()

            if (requestAmount == 0) {
                zeroAmountCount++
                return@filter false
            }
            if (requestAmount < 0) {
                return RequestOrderResultDTO.setNotPositiveError()
            }

            if (requestAmount > existItem.amount) {
                return RequestOrderResultDTO.setRequestOrderAmountFailResultDTO()
            } else {
                existItem.amount -= item.amount
                return@filter true
            }
        }

        if (zeroAmountCount == requestOrderDTO.orderItems.size)
            return RequestOrderResultDTO.setRequestOrderAllZeroFailResultDTO()

        itemRepository.saveAll(orderMap.values.toList())

        val member = memberRepository.getReferenceById(requestOrderDTO.memberId)

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
        val orderItemList: MutableList<OrderItem> = mutableListOf()
        requestOrderDTO.orderItems.map { info ->
            val item = orderMap[info.itemId] ?: throw NoSuchElementException()
            orderItemList.add(
                OrderItem.fromOrderItemDTO(
                    OrderItemDTO.fromOrderRequestInfo(orders, item, info.amount)
                )
            )
        }
        orderItemRepository.saveAll(orderItemList)
        println("주문 완료")
        return RequestOrderResultDTO.setRequestOrderSuccessResultDTO()
    }

    //주소 가져오기
    override fun getAddress(memberId: Long): AddressDTO {
        return AddressDTO.fromMember(memberRepository.getReferenceById(memberId))
    }

    override fun getShopOrders(memberId: Long, pageable: Pageable): OrdersSearchDTO {
        val findOrdersInfo = adminOrderRepository.findOrdersInfoByMemberId(memberId, pageable)

        return OrdersSearchDTO.toFormDTO(findOrdersInfo, "")
    }

    @Transactional
    override fun cancelOneOrder(orderId: Long): CartCancelResponseDTO {
        val ordersExisted = adminOrderRepository.existsById(orderId)

        return if (!ordersExisted) {
            CartCancelResponseDTO.toFailCancelOrderResponseDTO()

        } else {
            val orders = orderRepository.getReferenceById(orderId)

            when {
                orders.orderStatus == OrderStatus.CANCEL -> {
                    CartCancelResponseDTO.toFailCancelOrderByDuplicatedResponseDTO()

                }
                orders.deliveryStatus == DeliveryStatus.COMPLETE -> {
                    CartCancelResponseDTO.toFailCancelOrderByCompleteDeliveryResponseDTO()

                }
                else -> {
                    orders.cancelOrder()

                    val orderItems = orderItemRepository.getOrderItemByOrdersId(orderId)

                    orderItems.map { orderItem ->
                        orderItem.cancel()
                    }

                    CartCancelResponseDTO.toSuccessCancelOrderResponseDTO()
                }
            }
        }
    }
}
