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

        fun setRequestOrderNotExistedItemResultDTO() : CartResponseDTO {
            return CartResponseDTO(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이 존재하여 주문하지 못했습니다.",
                href = "/items"
            )
        }
    }
}
