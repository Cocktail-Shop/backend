package com.lionTF.cshop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

//request body를 저장하기 위한 dto
data class AddCartItemDTO(
    val memberId: Long?,
    val itemId: Long,
    val amount: Int,
)

data class AddCartItemRequestDTO(
    val itemId: Long,
    var amount: Int = 1,
)

data class AddCartItemResultDTO(
    val status: Int,
    val message: String,
    var href: String,
){
    companion object{
        // 장바구니 상품 추가 성공시 reponseBody에 저장되는 함수
        fun setSuccessAddCartItemResultDTO() : AddCartItemResultDTO{
            return AddCartItemResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "장바구니에 상품을 추가하였습니다.",
                href = "/items"
            )
        }

        // 장바구니 상품 추가 실패시 reponseBody에 저장되는 함수: 삭제된 아이템인 경우
        fun setStatusFailAddCartItemResultDTO() : AddCartItemResultDTO{
            return AddCartItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이어서 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        // 장바구니 상품 추가 실패시 reponseBody에 저장되는 함수: 재고가 부족한 경우
        fun setAmountFailAddCartItemResultDTO() : AddCartItemResultDTO{
            return AddCartItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "해당 상품의 재고가 부족하여 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        // 장바구니 상품 추가 실패시 reponseBody에 저장되는 함수: 재고가 부족한 경우
        fun setNotPositiveError() : AddCartItemResultDTO{
            return AddCartItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "상품 수량은 양수여야 합니다.",
                href = "/items"
            )
        }
    }
}





