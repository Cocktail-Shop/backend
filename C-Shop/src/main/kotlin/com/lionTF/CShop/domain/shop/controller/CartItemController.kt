package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.createItemResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.AddCartItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.AddCartItemResultDTO
import com.lionTF.CShop.domain.shop.service.CartItemService
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@RestController
class CartItemController(
    private val cartItemService: CartItemService,
) {
    // 장바구니 상품 추가
    @PostMapping("/items/cart")
    fun addItemToCart(@RequestBody addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO {
        return cartItemService.addCartItem(addCartItemDTO)
    }
}