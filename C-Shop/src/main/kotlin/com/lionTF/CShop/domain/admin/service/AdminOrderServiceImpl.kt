package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminOrderRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.CShop.domain.shop.models.Orders
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AdminOrderServiceImpl(

    private val adminOrderRepository: AdminOrderRepository,

) : AdminOrderService {

    // 주문 삭제
    override fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO {

        if (formToExistedItems(deleteOrdersDTO.orderIds)) {
            for (orderId in deleteOrdersDTO.orderIds) {
                val orders = adminOrderRepository.getReferenceById(orderId)
                orders.deleteOrder()
            }

            return setDeleteSuccessOrdersResultDTO()
        } else {
            return setDeleteFailOrdersResultDTO()
        }

    }


    // 주문 전체 조회
    override fun getAllOrders(pageable: Pageable): GetAllOrdersResultDTO {
        val ordersInfo = adminOrderRepository.findOrdersInfo(pageable)

        return GetAllOrdersResultDTO(
            HttpStatus.OK.value(),
            "주문 조회가 성공했습니다.",
            ordersInfo
        )
    }

    // 회원 ID로 주문 조회
    override fun getOrdersByMemberId(keyword: String, pageable: Pageable): GetAllOrdersResultDTO {

        val findOrdersInfo = adminOrderRepository.findOrdersInfoByMemberId(keyword, pageable)

        return GetAllOrdersResultDTO(
            HttpStatus.OK.value(),
            "주문 조회가 성공했습니다.",
            findOrdersInfo
        )
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
}