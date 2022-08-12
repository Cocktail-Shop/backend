package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.service.CartItemService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*

@Controller
class CartItemController(
    private val cartItemService: CartItemService,
) {
    // 장바구니 상품 추가
    @PostMapping("/items/cart")
    fun addItemToCart(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("addCartItemRequestDTO") addCartItemRequestDTO: AddCartItemRequestDTO): String{
        val addCartItemDTO = AddCartItemDTO(authMemberDTO?.memberId,addCartItemRequestDTO.itemId,addCartItemRequestDTO.amount)
        cartItemService.addCartItem(addCartItemDTO)
        return "redirect:/items"
    }

    @PostMapping("/items/cocktails/cart")
    fun addCocktailItemToCart(@RequestBody addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO {
        return cartItemService.addCartCocktailItem(addCartCocktailItemDTO)
    }
}