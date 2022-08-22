package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.OrderService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class OrderController(
    private val orderService: OrderService,
    private val adminOrderService: AdminOrderService,
) {
    //상품 주문
    @PostMapping("/orders")
    fun createOrder(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        requestOrderInfoDTO: RequestOrderInfoDTO,
        model: Model
    ): String {

        val requestOrderDTO = RequestOrderDTO(
            authMemberDTO?.memberId,
            requestOrderInfoDTO.orderItems,
            requestOrderInfoDTO.address,
            requestOrderInfoDTO.addressDetail
        )
        model.addAttribute("result", orderService.requestOrder(requestOrderDTO))
        return "global/message"
    }

    //기존 배송지 가져오기
    @GetMapping("/getAddress")
    fun getAddress(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, model: Model): String {
        model.addAttribute("address", authMemberDTO?.memberId?.let { orderService.getAddress(it) })
        return "redirect:/shop/singleItem"
    }

    @PostMapping("/orders/cart/items")
    fun createCartOrder(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        @ModelAttribute("requestOrderInfoDTO") requestOrderInfoDTO: RequestOrderInfoDTO,
        model: Model
    ): String {

        // TODO 정적 팩토리 패턴으로 리펙토링 예정
        val requestCartOrderDTO = RequestOrderDTO(
            authMemberDTO?.memberId,
            requestOrderInfoDTO.orderItems,
            requestOrderInfoDTO.address,
            requestOrderInfoDTO.addressDetail
        )
        model.addAttribute("result", orderService.requestOrder(requestCartOrderDTO))
        return "global/message"
    }

    @DeleteMapping("/orders/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: Long, model: Model): String {
        model.addAttribute("result", adminOrderService.cancelOneOrder(orderId))
        return "global/message"
    }

    @GetMapping("/orders")
    fun getOrders(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, pageable: Pageable, model: Model): String {
        model.addAttribute("orders", orderService.getShopOrders(pageable))
        return "shop/orderList"
    }
}
