package com.lionTF.cshop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class CartResponseDTO(
    val httpStatus: Int,
    val message: String,
    var href: String = ""
) {

    companion object {
        fun toSuccessDeleteItemResponseDTO(): CartResponseDTO {
            return CartResponseDTO(HttpStatus.NO_CONTENT.value(), "상품이 정삭적으로 삭제되었습니다.", "/cart")
        }

        fun toFailFindCartIdResponseDTO(): CartResponseDTO {
            return CartResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "회원의 장바구니가 존재하지 않습니다.", "/cart")
        }
    }
}
