package com.lionTF.CShop.domain.shop.service.shopinterface

import com.lionTF.CShop.domain.shop.controller.dto.AddCartCocktailItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.AddCartCocktailItemResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.AddCartItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.AddCartItemResultDTO


interface CartItemService {
    //장바구니에 단일 상품 추가 메소드
    fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO

    //장바구니에 칵테일 상품 재료들 추가 메소드
    fun  addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO
}