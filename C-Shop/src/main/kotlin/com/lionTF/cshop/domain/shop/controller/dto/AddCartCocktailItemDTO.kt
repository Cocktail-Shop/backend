package com.lionTF.cshop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class AddCartCocktailItemDTO(
    val memberId: Long,
    val items: MutableList<AddCartCocktailItemInfoDTO>
) {
    companion object {
        fun toAddCartCocktailItemDTO(
            memberId: Long,
            addCartCocktailItemRequestDTO: AddCartCocktailItemRequestDTO
        ): AddCartCocktailItemDTO {
            return AddCartCocktailItemDTO(
                memberId,
                addCartCocktailItemRequestDTO.items
            )
        }
    }
}

data class AddCartCocktailItemRequestDTO(
    val items: MutableList<AddCartCocktailItemInfoDTO> = mutableListOf()
) {

    companion object {
        fun toFormRequestCocktailCartInfoDTO(cocktailInfoDTO: MutableList<AddCartCocktailItemInfoDTO>): AddCartCocktailItemRequestDTO {
            return AddCartCocktailItemRequestDTO(
                items = cocktailInfoDTO
            )
        }
    }
}


data class AddCartCocktailItemInfoDTO(
    var itemId: Long = 0,
    var amount: Int = 0,
    var price: Int = 0,
) {

    companion object {
        fun fromCocktailItemDTO(cocktailItemDTO: CocktailItemDTO): AddCartCocktailItemInfoDTO {
            return AddCartCocktailItemInfoDTO(
                itemId = cocktailItemDTO.itemId,
                amount = 0,
                price = cocktailItemDTO.price,
            )
        }
    }
}

data class AddCartCocktailItemResultDTO(
    val status: Int,
    val message: String,
    val href: String,
) {

    companion object {
        fun setSuccessAddCartCocktailItemResultDTO(): AddCartCocktailItemResultDTO {
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "장바구니에 상품들을 추가하였습니다.",
                href = "/items"
            )
        }

        fun setStatusFailAddCartCocktailItemResultDTO(): AddCartCocktailItemResultDTO {
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이 존재하여 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        fun setAmountFailAddCartCocktailItemResultDTO(): AddCartCocktailItemResultDTO {
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "재고가 부족한 상품이 존재하여 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        fun setNotPositiveError(): AddCartCocktailItemResultDTO {
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "요청 상품 수량은 양수여야 합니다.",
                href = "/items"
            )
        }

        fun setAllZeroFailAddCartCocktailItemResultDTO(): AddCartCocktailItemResultDTO {
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "적어도 하나 이상의 상품을 담으셔야 합니다.",
                href = "/items"
            )
        }
    }
}
