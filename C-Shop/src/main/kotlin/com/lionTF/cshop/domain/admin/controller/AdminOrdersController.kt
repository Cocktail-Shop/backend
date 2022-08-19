package com.lionTF.cshop.domain.admin.controller

import com.lionTF.cshop.domain.admin.service.admininterface.AdminOrderService
import org.springframework.data.domain.Pageable
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
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("orders", adminOrderService.getAllOrders(pageable))
        return "admins/order/getAllOrder"
    }

    // 회원 ID or 상품명으로 주문 조회
    @GetMapping("members/orders")
    fun getOrdersByMemberId(
        @RequestParam("keyword") keyword: String,
        pageable: Pageable,
        model: Model
    ): String {
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

    @PutMapping("orders/{orderId}/in-delivery")
    fun updateDeliveryStatus(
        @PathVariable("orderId") orderId: Long,
        model: Model
    ): String {
        model.addAttribute("result", adminOrderService.updateDeliveryInDelivery(orderId))
        return "global/message"
    }

    @PutMapping("orders/{orderId}/delivery/complete")
    fun updateDeliveryStatusComplete(
        @PathVariable("orderId") orderId: Long,
        model: Model
    ): String {
        model.addAttribute("result", adminOrderService.updateDeliveryComplete(orderId))
        return "global/message"
    }

    // 하나 이상의 주문 취소
//    @DeleteMapping("orders")
//    fun deleteOrders(@RequestBody deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO {
//        return adminOrderService.deleteOrders(deleteOrdersDTO)
//    }
}
