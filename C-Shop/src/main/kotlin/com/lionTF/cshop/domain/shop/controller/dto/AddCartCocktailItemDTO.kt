package com.lionTF.cshop.domain.shop.controller.dto


import org.springframework.http.HttpStatus

data class AddCartCocktailItemDTO(
    val memberId: Long,
    val items: MutableList<AddCartCocktailItemInfoDTO>
)

data class AddCartCocktailItemRequestDTO(
    val items: MutableList<AddCartCocktailItemInfoDTO>
){
    companion object{
        fun toFormRequestCocktailCartInfoDTO(cocktailInfoDTO: MutableList<AddCartCocktailItemInfoDTO>):AddCartCocktailItemRequestDTO{
            return AddCartCocktailItemRequestDTO(
                items = cocktailInfoDTO
            )
        }
    }
}

data class AddCartCocktailItemInfoDTO(
    var itemId: Long,
    var amount: Int,
    var price: Int,
){
    companion object{
        fun fromCocktailItemDTO(cocktailItemDTO: CocktailItemDTO) : AddCartCocktailItemInfoDTO{
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
){
    companion object{
        // 장바구니 칵테일 재료 상품들 추가 성공시 reponseBody에 저장되는 함수
        fun setSuccessAddCartCocktailItemResultDTO() : AddCartCocktailItemResultDTO{
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "장바구니에 상품들을 추가하였습니다.",
                href = "/items"
            )
        }

        // 장바구니 칵테일 재료 상품들 추가 실패시 reponseBody에 저장되는 함수 : 삭제된 아이템 존재
        fun setStatusFailAddCartCocktailItemResultDTO() : AddCartCocktailItemResultDTO{
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이 존재하여 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        // 장바구니 칵테일 재료 상품들 추가 실패시 reponseBody에 저장되는 함수: 재고 부족
        fun setAmountFailAddCartCocktailItemResultDTO() : AddCartCocktailItemResultDTO{
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "재고가 부족한 상품이 존재하여 장바구니에 담지 못하였습니다.",
                href = "/items"
            )
        }

        // 장바구니 칵테일 재료 상품들 추가 실패시 reponseBody에 저장되는 함수: 재고 부족
        fun setNotPositiveError() : AddCartCocktailItemResultDTO{
            return AddCartCocktailItemResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "요청 상품 수량은 양수여야 합니다.",
                href = "/items"
            )
        }

    }
}






