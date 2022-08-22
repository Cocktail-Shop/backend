package com.lionTF.cshop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class AddCartItemDTO(
    val memberId: Long?,
    val itemId: Long,
    val amount: Int,
)

data class AddCartItemRequestDTO(
    val itemId: Long,
    var amount: Int = 0,
)

data class AddCartItemResultDTO(
    val status: Int,
    val message: String,
    var href: String,
) {

    companion object {
        fun setSuccessAddCartItemResultDTO(): AddCartItemResultDTO {
            return AddCartItemResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "장바구니에 상품을 추가하였습니다.",
                href = "/items"
            )
        }

        fun setStatusFailAddCartItemResultDTO(): AddCartItemResultDTO {
            return AddCartItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이어서 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        fun setAmountFailAddCartItemResultDTO(): AddCartItemResultDTO {
            return AddCartItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "해당 상품의 재고가 부족하여 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        fun setNotPositiveError(): AddCartItemResultDTO {
            return AddCartItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "상품 수량은 양수여야 합니다.",
                href = "/items"
            )
        }
    }
}
