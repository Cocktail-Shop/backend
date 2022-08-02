package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderResultDTO
import com.lionTF.CShop.domain.shop.service.OrderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController


@RestController
class OrderController(
    private val orderService: OrderService,
) {
    //상품 주문
    @PostMapping("/orders")
    fun createOrder(@RequestBody requestOrderDTO: RequestOrderDTO) : RequestOrderResultDTO {
        return orderService.requestOrder(requestOrderDTO)
    }

}