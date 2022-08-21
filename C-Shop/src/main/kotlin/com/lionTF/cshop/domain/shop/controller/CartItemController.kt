package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.admin.controller.dto.DeleteCartItemDTO
import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.service.shopinterface.CartItemService
import com.lionTF.cshop.domain.admin.controller.dto.DeleteCartItemRequestDTO
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
    fun addCocktailItemToCart(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @ModelAttribute("addCartCocktailItemDTO") addCartCocktailItemRequestDTO: AddCartCocktailItemRequestDTO, model: Model) : String {
        val addCartCocktailItemDTO =
            authMemberDTO?.memberId?.let { AddCartCocktailItemDTO(it, addCartCocktailItemRequestDTO.items) }
        model.addAttribute("result", addCartCocktailItemDTO?.let { cartItemService.addCartCocktailItem(it) })
        return "global/message"
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/cart/items")
    fun deleteCartItem(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @RequestBody deleteCartItemRequestDTO: DeleteCartItemRequestDTO, model: Model): String {
        val deleteCartItemDTO = DeleteCartItemDTO(authMemberDTO?.memberId, deleteCartItemRequestDTO.cartItemID, deleteCartItemRequestDTO.itemIds)
        model.addAttribute("result", cartItemService.deleteCartItem(deleteCartItemDTO))
        return "global/message"
    }

    // 장바구니 상품 조회
    @GetMapping("/items/cart")
    fun getCartItem(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @PageableDefault(size = 2) pageable: Pageable, model: Model): String {
        model.addAttribute("searchCart", cartItemService.getCart(pageable))
        return "global/message"
    }
}

