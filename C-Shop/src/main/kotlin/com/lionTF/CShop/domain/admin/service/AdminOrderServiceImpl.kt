package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminOrderItemRepository
import com.lionTF.CShop.domain.admin.repository.AdminOrderRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.domain.shop.repository.OrderItemRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AdminOrderServiceImpl(

    private val adminOrderRepository: AdminOrderRepository,
    private val adminOrderItemRepository: AdminOrderItemRepository

) : AdminOrderService {

    // 하나의 주문 취소
    override fun cancelOneOrder(orderId: Long): AdminResponseDTO {
        val existsOrder = adminOrderRepository.existsById(orderId)

        return if (!existsOrder) {
            AdminResponseDTO.toFailDeleteOrderResponseDTO()

        } else {
            val order = adminOrderRepository.getReferenceById(orderId)
            order.cancelOrder()

            val orderItem = adminOrderItemRepository.getOrderItemByOrdersId(orderId)
            orderItem.cancel()

            AdminResponseDTO.toSuccessDeleteOrderResponseDTO()
        }
    }


    // 주문 전체 조회
    override fun getAllOrders(pageable: Pageable): ResponseSearchOrdersResultDTO {
        val findOrdersInfo = adminOrderRepository.findOrdersInfo(pageable)

        return ResponseSearchOrdersResultDTO.orderToResponseOrderSearchPageDTO(findOrdersInfo, "")
    }

    // 회원 ID로 주문 조회
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
                true -> {return false}
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