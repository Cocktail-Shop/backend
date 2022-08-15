package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.CShop.domain.shop.service.shopinterface.OrderService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class OrderController(
    private val orderService: OrderService,
) {
    //상품 주문
    @PostMapping("/orders")
    fun createOrder(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("requestOrderInfoDTO") requestOrderInfoDTO: RequestOrderInfoDTO, model: Model) : String {
        val requestOrderDTO = RequestOrderDTO(authMemberDTO?.memberId,requestOrderInfoDTO.orderItems,requestOrderInfoDTO.address,requestOrderInfoDTO.addressDetail)
        model.addAttribute("result",orderService.requestOrder(requestOrderDTO))
        return "global/message"
    }

    //기존 배송지 가져오기
    @GetMapping("/getAddress")
    fun getAddress(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, model: Model): String{
        model.addAttribute("address", authMemberDTO?.memberId?.let { orderService.getAddress(it) })
        return "redirect:/shop/singleItem"
    }

    // 장바구니 상품 주문
    // cartItem을 통해 cart에 있는 Item을 지우는 것 외에 동일
    // TODO : cartItem을 통해 cart에 있는 Item을 지우는 것 -> 장바구니 상품 삭제 활용
    @PostMapping("/orders/cart/items")
    fun createCartOrder(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("requestOrderInfoDTO") requestOrderInfoDTO: RequestOrderInfoDTO, model: Model) : String {
        val requestCartOrderDTO = RequestOrderDTO(authMemberDTO?.memberId, requestOrderInfoDTO.orderItems,requestOrderInfoDTO.Address,requestOrderInfoDTO.AddressDetail)
        model.addAttribute("result", orderService.requestOrder(requestCartOrderDTO))
        return "global/message"
    }

    // 주문 취소
    @DeleteMapping("/orders/{orderId}")
    fun cancelOrder(@PathVariable("orderId") orderId: Long, model: Model): String {
        model.addAttribute("result", orderService.cancelOrder(orderId))
        return "global/message"
    }

    // 주문 조회
    @GetMapping("/orders")
    fun getOrders(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @PageableDefault(size = 2) pageable: Pageable, model: Model): String {
        model.addAttribute("searchOrders", orderService.getShopOrders(pageable))
        return "global/message"
    }
}