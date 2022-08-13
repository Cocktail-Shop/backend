package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.service.shopinterface.CartItemService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class CartItemController(
    private val cartItemService: CartItemService,
) {
    // 장바구니 상품 추가
    @PostMapping("/items/cart")
    fun addItemToCart(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("addCartItemRequestDTO") addCartItemRequestDTO: AddCartItemRequestDTO, model: Model): String{
        val addCartItemDTO = AddCartItemDTO(authMemberDTO?.memberId,addCartItemRequestDTO.itemId,addCartItemRequestDTO.amount)
        model.addAttribute("result",cartItemService.addCartItem(addCartItemDTO))
        return "global/message"
    }

    @PostMapping("/items/cocktails/cart")
    fun addCocktailItemToCart(@RequestBody addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO {
        return cartItemService.addCartCocktailItem(addCartCocktailItemDTO)
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/cart/items/{itemId}")
    fun deleteCart(@PathVariable("itemId") itemId: Long, model: Model): String {
        model.addAttribute("result", cartItemService.deleteCart(itemId))
        return "global/message"
    }

    // 장바구니 상품 조회
    @GetMapping("/items/cart")
    fun getCart(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @PageableDefault(size = 2) pageable: Pageable, model: Model): String {
        model.addAttribute("searchCart", cartItemService.getCart(pageable))
        return "global/message"
    }
}