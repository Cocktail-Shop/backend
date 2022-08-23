package com.lionTF.cshop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class CartResponseDTO(
    val httpStatus: Int,
    val message: String,
    var href: String = ""
) {

    companion object {
        fun toSuccessDeleteItemResponseDTO(): CartResponseDTO {
            return CartResponseDTO(HttpStatus.NO_CONTENT.value(), "상품이 정삭적으로 삭제되었습니다.", "/items/cart")
        }
    }
}
