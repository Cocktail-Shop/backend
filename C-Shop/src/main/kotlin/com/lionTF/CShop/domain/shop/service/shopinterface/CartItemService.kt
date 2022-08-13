package com.lionTF.CShop.domain.shop.service.shopinterface

import com.lionTF.CShop.domain.shop.controller.dto.*
import org.springframework.data.domain.Pageable


interface CartItemService {
    //장바구니에 단일 상품 추가 메소드
    fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO

    //장바구니에 칵테일 상품 재료들 추가 메소드
    fun  addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO

    // 장바구니 상품 삭제
    fun deleteCart(itemId: Long): CartResponseDTO

    // 장바구니 상품 조회
    fun getCart(pageable: Pageable): ResponseSearchCartResultDTO
}