package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminOrdersController(
    private val adminOrderService: AdminOrderService,
) {

    // 주문 삭제
    @DeleteMapping("/admins/orders")
    fun deleteOrders(@RequestBody deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO {
        return adminOrderService.deleteOrders(deleteOrdersDTO)
    }
}