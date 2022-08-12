package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderResultDTO
import com.lionTF.CShop.domain.shop.service.OrderService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class OrderController(
    private val orderService: OrderService,
) {
    //상품 주문
    @PostMapping("/orders")
    fun createOrder(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("requestOrderInfoDTO") requestOrderInfoDTO: RequestOrderInfoDTO) : String {
        val requestOrderDTO = RequestOrderDTO(authMemberDTO?.memberId,requestOrderInfoDTO.orderItems,requestOrderInfoDTO.orderAddress)
        orderService.requestOrder(requestOrderDTO)
        return "redirect:/items"
    }

}