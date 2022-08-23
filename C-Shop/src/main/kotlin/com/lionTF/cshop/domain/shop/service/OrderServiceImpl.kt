package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.controller.dto.OrdersSearchDTO
import com.lionTF.cshop.domain.admin.repository.AdminOrderRepository
import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.models.DeliveryStatus
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
) : OrderService {
    @Transactional
    override fun requestOrder(requestOrderDTO: RequestOrderDTO): RequestOrderResultDTO {
        var zeroAmountCount = 0

        requestOrderDTO.orderItems.map {
            val requestAmount = it.amount
            val existAmount = itemRepository.getReferenceById(it.itemId).amount

            if (requestAmount == 0) zeroAmountCount++

            if (requestAmount < 0) {
                return RequestOrderResultDTO.setNotPositiveError()
            }

            if (itemRepository.getReferenceById(it.itemId).itemStatus) {
                if (requestAmount > existAmount) return RequestOrderResultDTO.setRequestOrderAmountFailResultDTO()
            } else return RequestOrderResultDTO.setRequestOrderStatusFailResultDTO()
        }

        if (zeroAmountCount == requestOrderDTO.orderItems.size) return RequestOrderResultDTO.setRequestOrderAllZeroFailResultDTO()

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

        requestOrderDTO.orderItems.map { info ->
            val item = itemRepository.getReferenceById(info.itemId)
            if (info.amount > 0) {
                item.amount -= info.amount
                itemRepository.save(item)

                orderItemRepository.save(
                    OrderItem.fromOrderItemDTO(
                        OrderItemDTO.fromOrderRequestInfo(orders, item, info.amount)
                    )
                )
            }
        }
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

                    orderItems.forEach { orderItem ->
                        orderItem.cancel()
                    }

                    CartCancelResponseDTO.toSuccessCancelOrderResponseDTO()
                }
            }
        }
    }
}
