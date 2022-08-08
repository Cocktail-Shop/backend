package com.lionTF.CShop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class AddCartCocktailItemDTO(
    val memberId: Long,
    val items: List<AddCartCocktailItemInfoDTO>
)

data class AddCartCocktailItemInfoDTO(
    val itemId: Long,
    val amount: Int
)

data class AddCartCocktailItemResultDTO(
    val status: Int,
    val message: String,
    val errorItems: List<Long>
)

// 장바구니 칵테일 재료 상품들 추가 성공시 reponseBody에 저장되는 함수
fun setSuccessAddCartCocktailItemResultDTO(errorItems: List<Long>) : AddCartCocktailItemResultDTO{
    return AddCartCocktailItemResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "장바구니에 상품들을 추가하였습니다.",
        errorItems = errorItems
    )
}

// 장바구니 칵테일 재료 상품들 추가 실패시 reponseBody에 저장되는 함수
fun setFailAddCartCocktailItemResultDTO(errorItems: List<Long>) : AddCartCocktailItemResultDTO{
    return AddCartCocktailItemResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "상품의 재고가 부족하거나 삭제된 상품이 존재하여 장바구니에 담지 못하였습니다.",
        errorItems = errorItems
    )
}

