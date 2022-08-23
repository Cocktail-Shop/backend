package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.shop.controller.dto.*
import org.springframework.data.domain.Pageable


interface CartItemService {

    fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO

    fun addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO): AddCartCocktailItemResultDTO

    fun deleteCartItem(cartItemId: Long): CartResponseDTO

    fun getCart(memberId: Long, pageable: Pageable): ResponseSearchCartResultDTO
}
