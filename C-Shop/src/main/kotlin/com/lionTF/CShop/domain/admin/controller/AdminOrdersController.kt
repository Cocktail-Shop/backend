package com.lionTF.CShop.domain.admin.controller

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

    // 전체 주문 조회
    @GetMapping("orders")
    fun getAllOrders(
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        val allOrders = adminOrderService.getAllOrders(pageable)
        model.addAttribute("orders", adminOrderService.getAllOrders(pageable))
        return "admins/order/getAllOrder"
    }

    // 회원 ID로 주문 조회
    @GetMapping("members/orders")
    fun getOrdersByMemberId(
        @RequestParam("keyword") keyword: String,
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ) : String {
        model.addAttribute("searchOrders", adminOrderService.getOrdersByMemberId(keyword, pageable))
        return "admins/order/getMemberOrder"
    }

    // 하나의 주문 취소
    @DeleteMapping("orders/{orderId}")
    fun deleteOneOrder(
        @PathVariable("orderId") orderId: Long,
        model: Model
    ): String {
        model.addAttribute("result", adminOrderService.cancelOneOrder(orderId))
        return "global/message"
    }

    // 하나 이상의 주문 취소
//    @DeleteMapping("orders")
//    fun deleteOrders(@RequestBody deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO {
//        return adminOrderService.deleteOrders(deleteOrdersDTO)
//    }
}