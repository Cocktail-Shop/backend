package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admins")
class AdminOrdersController(
    private val adminOrderService: AdminOrderService,
) {

    // 주문 삭제
    @DeleteMapping("orders")
    fun deleteOrders(@RequestBody deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO {
        return adminOrderService.deleteOrders(deleteOrdersDTO)
    }

    // 전체 주문 조회
    @GetMapping("orders")
    fun getAllOrders(model: Model): GetAllOrdersResultDTO {
        return adminOrderService.getAllOrders()
    }
}