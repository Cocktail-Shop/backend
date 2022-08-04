package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteFailOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteSuccessOrdersResultDTO
import com.lionTF.CShop.domain.admin.repository.AdminOrderRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.CShop.domain.shop.models.Orders
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

    // 존재하는 주문인지 검사하는 함수
    override fun existedOrder(OrderId: Long): Optional<Orders> {
        return adminOrderRepository.findById(OrderId)
    }

    // Form으로부터 받아온 orderId들이 존재하는 주문인지 검사
    override fun formToExistedItems(orderList: MutableList<Long>): Boolean {
        for (orderId in orderList) {
            when (existedOrder(orderId).isEmpty) {
                true -> {return false}
            }
        }
        return true
    }
}