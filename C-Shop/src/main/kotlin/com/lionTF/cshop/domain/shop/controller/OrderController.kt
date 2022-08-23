package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.cshop.domain.shop.repository.ItemRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.CartItemService
import com.lionTF.cshop.domain.shop.service.shopinterface.OrderService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class OrderController(
    private val cartItemService: CartItemService,
    private val orderService: OrderService
) {

    @PostMapping("/orders")
    fun createOrder(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        requestOrderInfoDTO: RequestOrderInfoDTO,
        model: Model
    ): String {
        val requestOrderDTO = RequestOrderDTO.toRequestOrderDTO(authMemberDTO.memberId, requestOrderInfoDTO)
        model.addAttribute("result", orderService.requestOrder(requestOrderDTO))
        return "global/message"
    }

    @GetMapping("/getAddress")
    fun getAddress(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO, model: Model): String {
        model.addAttribute("address", orderService.getAddress(authMemberDTO.memberId))
        return "redirect:/shop/singleItem"
    }

    @PostMapping("/orders/cart/items/{cartItemId}")
    fun createCartOrder(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("cartItemId") cartItemId: Long,
        requestOrderInfoDTO: RequestOrderInfoDTO,
        model: Model
    ): String {
        val requestCartOrderDTO = RequestOrderDTO.toRequestOrderDTO(authMemberDTO.memberId, requestOrderInfoDTO)
        model.addAttribute("result", orderService.requestOrder(requestCartOrderDTO))
        cartItemService.deleteCartItem(cartItemId)
        return "global/message"
    }

    @DeleteMapping("/orders/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: Long, model: Model): String {
        model.addAttribute("result", orderService.cancelOneOrder(orderId))
        return "global/message"
    }

    @GetMapping("/orders")
    fun getOrders(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("orders", orderService.getShopOrders(authMemberDTO.memberId, pageable))
        return "shop/orderList"
    }
}
