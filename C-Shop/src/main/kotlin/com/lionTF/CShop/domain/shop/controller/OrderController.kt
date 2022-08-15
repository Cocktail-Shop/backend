package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.CShop.domain.shop.service.shopinterface.OrderService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


@Controller
class OrderController(
    private val orderService: OrderService,
) {
    //상품 주문
    @PostMapping("/orders")
    fun createOrder(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, requestOrderInfoDTO: RequestOrderInfoDTO, model: Model) : String {
        println(requestOrderInfoDTO)
        val requestOrderDTO = RequestOrderDTO.toRequestOrderDTO(authMemberDTO?.memberId,requestOrderInfoDTO)
        model.addAttribute("result",orderService.requestOrder(requestOrderDTO))
        return "global/message"
    }

    //기존 배송지 가져오기
    @GetMapping("/getAddress")
    fun getAddress(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, model: Model): String{
        model.addAttribute("address", authMemberDTO?.memberId?.let { orderService.getAddress(it) })
        return "redirect:/shop/singleItem"
    }

}