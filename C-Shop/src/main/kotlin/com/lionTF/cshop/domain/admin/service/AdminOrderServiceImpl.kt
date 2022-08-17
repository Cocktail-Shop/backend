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
import java.util.*
import javax.transaction.Transactional

@Service
class AdminOrderServiceImpl(

    private val adminOrderRepository: AdminOrderRepository,
    private val adminOrderItemRepository: AdminOrderItemRepository

) : AdminOrderService {

    @Transactional
    override fun cancelOneOrder(orderId: Long): AdminResponseDTO {
        val existsOrder = adminOrderRepository.existsById(orderId)

        return if (!existsOrder) {
            AdminResponseDTO.toFailCancelOrderResponseDTO()

        } else {
            val order = adminOrderRepository.getReferenceById(orderId)

            if (order.orderStatus == OrderStatus.CANCEL) {
                AdminResponseDTO.toFailCancelOrderByDuplicatedResponseDTO()

            } else if (order.deliveryStatus == DeliveryStatus.COMPLETE) {
                AdminResponseDTO.toFailCancelOrderByCompleteDeliveryResponseDTO()

            } else {
                order.cancelOrder()

                val orderItem = adminOrderItemRepository.getOrderItemByOrdersId(orderId)
                orderItem.cancel()

                AdminResponseDTO.toSuccessCancelOrderResponseDTO()
            }
        }
    }

    override fun getAllOrders(pageable: Pageable): ResponseSearchOrdersResultDTO {
        val findOrdersInfo = adminOrderRepository.findOrdersInfo(pageable)

        return ResponseSearchOrdersResultDTO.orderToResponseOrderSearchPageDTO(findOrdersInfo, "")
    }

    override fun getOrdersByMemberId(keyword: String, pageable: Pageable): ResponseSearchOrdersResultDTO {
        val findOrdersInfoByMemberId = adminOrderRepository.findOrdersInfoByMemberId(keyword, pageable)

        return ResponseSearchOrdersResultDTO.orderToResponseOrderSearchPageDTO(findOrdersInfoByMemberId, keyword)
    }


    // 존재하는 주문인지 검사하는 함수
    private fun existedOrder(orderId: Long): Optional<Orders> {
        return adminOrderRepository.findById(orderId)
    }

    // Form으로부터 받아온 orderId들이 존재하는 주문인지 검사
    private fun formToExistedItems(orderList: MutableList<Long>): Boolean {
        for (orderId in orderList) {
            when (existedOrder(orderId).isEmpty) {
                true -> {
                    return false
                }
            }
        }
        return true
    }

//    // 하나 이상의 주문 취소
//    override fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO {
//
//        if (formToExistedItems(deleteOrdersDTO.orderIds)) {
//            for (orderId in deleteOrdersDTO.orderIds) {
//                val orders = adminOrderRepository.getReferenceById(orderId)
//                orders.deleteOrder()
//            }
//
//            return setDeleteSuccessOrdersResultDTO()
//        } else {
//            return setDeleteFailOrdersResultDTO()
//        }
//    }
}
