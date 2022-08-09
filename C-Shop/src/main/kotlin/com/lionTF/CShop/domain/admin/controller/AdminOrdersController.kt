package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseAllOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseAllOrdersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
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
    fun getAllOrders(
        model: Model,
        @PageableDefault(size = 2) pageable: Pageable
    ): String {
        model.addAttribute("orders", adminOrderService.getAllOrders(pageable))
        return "admins/order/getAllOrder"
    }

    // 회원 ID로 주문 조회
    @GetMapping("members/orders")
    fun getOrdersByMemberId(
        @RequestParam("keyword") keyword: String,
        model: Model,
        @PageableDefault(size = 2) pageable: Pageable
    ) : ResponseAllOrdersResultDTO {
        return adminOrderService.getOrdersByMemberId(keyword, pageable)
    }
}