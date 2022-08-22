package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.service.shopinterface.CartItemService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class CartItemController(
    private val cartItemService: CartItemService,
) {

    @PostMapping("/items/cart")
    fun addItemToCart(
        @ModelAttribute("addCartItemRequestDTO") addCartItemRequestDTO: AddCartItemRequestDTO,
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        model: Model
    ): String {
        val addCartItemDTO = AddCartItemDTO(authMemberDTO?.memberId, addCartItemRequestDTO.itemId, addCartItemRequestDTO.amount)
        model.addAttribute("result", cartItemService.addCartItem(addCartItemDTO))

        return "global/message"
    }

    @PostMapping("/items/cocktails/cart")
    fun addCocktailItemToCart(
        @ModelAttribute("addCartCocktailItemDTO") addCartCocktailItemRequestDTO: AddCartCocktailItemRequestDTO,
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        model: Model
    ): String {
        val addCartCocktailItemDTO = authMemberDTO?.memberId?.let {
            AddCartCocktailItemDTO(it, addCartCocktailItemRequestDTO.items)
        }

        model.addAttribute("result", addCartCocktailItemDTO?.let {
            cartItemService.addCartCocktailItem(it)
        })

        return "global/message"
    }

    @DeleteMapping("/cart/items")
    fun deleteCartItem(
        @ModelAttribute("deleteCartItemRequestDTO") deleteCartItemRequestDTO: DeleteCartItemRequestDTO,
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        model: Model
    ): String {

        // TODO 정적 팩토리 패턴으로 리펙토링 예정
        val deleteCartItemDTO = DeleteCartItemDTO(
            authMemberDTO?.memberId,
            deleteCartItemRequestDTO.cartItemID,
            deleteCartItemRequestDTO.itemIds
        )

        model.addAttribute("result", cartItemService.deleteCartItem(deleteCartItemDTO))
        return "global/message"
    }

    @GetMapping("/items/cart")
    fun getCartItem(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("carts", cartItemService.getCart(pageable))
        return "shop/cart"
    }
}
