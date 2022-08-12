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


@Controller
class OrderController(
    private val orderService: OrderService,
) {
    //상품 주문
    @PostMapping("/orders")
    fun createOrder(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("requestOrderInfoDTO") requestOrderInfoDTO: RequestOrderInfoDTO, model: Model) : String {
        val requestOrderDTO = RequestOrderDTO(authMemberDTO?.memberId,requestOrderInfoDTO.orderItems,requestOrderInfoDTO.Address,requestOrderInfoDTO.AddressDetail)
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