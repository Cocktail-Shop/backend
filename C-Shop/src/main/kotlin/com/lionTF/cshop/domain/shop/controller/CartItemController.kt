package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.service.shopinterface.CartItemService
import com.lionTF.cshop.domain.shop.service.shopinterface.OrderService
import org.springframework.data.domain.Pageable
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class CartItemController(
    private val cartItemService: CartItemService,
    private val orderService: OrderService,
) {

    @PostMapping("/items/cart")
    fun addItemToCart(
        @ModelAttribute("addCartItemRequestDTO") addCartItemRequestDTO: AddCartItemRequestDTO,
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        model: Model
    ): String {
        val addCartItemDTO =
            AddCartItemDTO(authMemberDTO.memberId, addCartItemRequestDTO.itemId, addCartItemRequestDTO.amount)
        model.addAttribute("result", cartItemService.addCartItem(addCartItemDTO))

        return "global/message"
    }

    @PostMapping("/items/cocktails/cart")
    fun addCocktailItemToCart(
        @ModelAttribute("addCartCocktailItemDTO") addCartCocktailItemRequestDTO: AddCartCocktailItemRequestDTO,
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        model: Model
    ): String {
        val addCartCocktailItemDTO = AddCartCocktailItemDTO(authMemberDTO.memberId, addCartCocktailItemRequestDTO.items)

        model.addAttribute("result", addCartCocktailItemDTO.let {
            cartItemService.addCartCocktailItem(it)
        })

        return "global/message"
    }

    @DeleteMapping("/cart/items/{cartItemId}")
    fun deleteCartItem(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("cartItemId") cartItemId: Long,
        model: Model
    ): String {
        model.addAttribute("result", cartItemService.deleteCartItem(cartItemId))
        return "global/message"
    }

    @GetMapping("/items/cart")
    fun getCartItem(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        pageable: Pageable,
        model: Model
    ): String {

        val address = orderService.getAddress(authMemberDTO.memberId)

        val cart = cartItemService.getCart(authMemberDTO.memberId, pageable)

        val cartInfoDTO = cart.result?.content?.map {
            RequestOrderItemDTO.fromCartItemDTO(it)
        }

        val requestOrderInfoDTO = RequestOrderInfoDTO.toFormRequestCocktailOrderInfoDTO(
            cartInfoDTO as MutableList<RequestOrderItemDTO>,
            address
        )
        model.addAttribute("carts", cart)
        model.addAttribute("requestOrderInfoDTO", requestOrderInfoDTO)
        return "shop/cart"
    }
}
