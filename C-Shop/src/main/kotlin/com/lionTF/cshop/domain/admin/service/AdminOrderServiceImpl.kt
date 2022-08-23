package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.repository.AdminOrderItemRepository
import com.lionTF.cshop.domain.admin.repository.AdminOrderRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.cshop.domain.shop.models.DeliveryStatus
import com.lionTF.cshop.domain.shop.models.OrderStatus
import com.lionTF.cshop.domain.shop.models.Orders
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminOrderServiceImpl(
    private val adminOrderRepository: AdminOrderRepository,
    private val adminOrderItemRepository: AdminOrderItemRepository
) : AdminOrderService {

    @Transactional
    override fun cancelOneOrder(orderId: Long): AdminResponseDTO {
        val ordersExisted = adminOrderRepository.existsById(orderId)

        return if (!ordersExisted) {
            AdminResponseDTO.toFailCancelOrderResponseDTO()

        } else {
            val orders = adminOrderRepository.getReferenceById(orderId)

            when {
                orders.orderStatus == OrderStatus.CANCEL -> {
                    AdminResponseDTO.toFailCancelOrderByDuplicatedResponseDTO()

                }
                orders.deliveryStatus == DeliveryStatus.COMPLETE -> {
                    AdminResponseDTO.toFailCancelOrderByCompleteDeliveryResponseDTO()

                }
                else -> {
                    orders.cancelOrder()

                    val orderItems = adminOrderItemRepository.getOrderItemByOrdersId(orderId)

                    orderItems.forEach { orderItem ->
                        orderItem.cancel()
                    }

                    AdminResponseDTO.toSuccessCancelOrderResponseDTO()
                }
            }
        }
    }

    override fun getAllOrders(pageable: Pageable): OrdersSearchDTO {
        val ordersInfo = adminOrderRepository.findOrdersInfo(pageable)

        return OrdersSearchDTO.toFormDTO(ordersInfo)
    }

    override fun getOrdersByMemberId(keyword: String, pageable: Pageable): OrdersSearchDTO {
        val orders = adminOrderRepository.findOrdersInfoByKeyword(keyword, pageable)

        return OrdersSearchDTO.toFormDTO(orders, keyword)
    }

    @Transactional
    override fun updateDeliveryInDelivery(orderId: Long): AdminResponseDTO {
        val orders = existedOrder(orderId)

        return when {
            orders == null -> {
                AdminResponseDTO.toFailUpdateDeliveryStatus()
            }
            orders.deliveryStatus == DeliveryStatus.REFUND -> {
                AdminResponseDTO.toFailUpdateDeliveryStatusByCancelOrder()
            }
            else -> {
                orders.updateDeliveryStatusInDelivery()
                AdminResponseDTO.toSuccessUpdateDeliveryStatusInDelivery()
            }
        }
    }

    @Transactional
    override fun updateDeliveryComplete(orderId: Long): AdminResponseDTO {
        val orders = existedOrder(orderId)

        return when {
            orders == null -> {
                AdminResponseDTO.toFailUpdateDeliveryStatus()
            }
            orders.deliveryStatus == DeliveryStatus.REFUND -> {
                AdminResponseDTO.toFailUpdateDeliveryStatusByCancelOrder()
            }
            else -> {
                orders.updateDeliveryStatusComplete()
                return AdminResponseDTO.toSuccessUpdateDeliveryStatusComplete()
            }
        }
    }


    // 존재하는 주문인지 검사하는 함수
    private fun existedOrder(orderId: Long): Orders? {
        return adminOrderRepository.findOrders(orderId)
    }
}
