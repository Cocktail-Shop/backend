package com.lionTF.CShop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class CartResponseDTO(val httpStatus: Int, val message: String, var href: String = "" ) {
    companion object {
        fun toSuccessDeleteItemResponseDTO(): CartResponseDTO {
            return CartResponseDTO(HttpStatus.NO_CONTENT.value(), "상품이 정삭적으로 삭제되었습니다.", "/cart")
        }

        fun toFailDeleteItemResponseDTO(): CartResponseDTO {
            return CartResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "존재하지 않는 상품입니다.", "/cart")
        }
    }
}