package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.http.HttpStatus

// 삭제할 상품의 ID 정보
data class DeleteCocktailDTO(
    var cocktailIds: MutableList<Long> = mutableListOf()
)

// 상태코드와 message를 반환하기 위한 DTO
data class DeleteCocktailResultDTO(
    val status: Int,
    val message: String,
)

// 삭제 성공시 reposneBody에 저장되는 함수
fun setDeleteSuccessCocktailResultDTO(): DeleteCocktailResultDTO {
    return DeleteCocktailResultDTO(
        status = HttpStatus.NO_CONTENT.value(),
        message = "칵테일 상품이 삭제되었습니다."
    )
}

// 삭제 실패시 reposneBody에 저장되는 함수
fun setDeleteFailCocktailResultDTO(): DeleteCocktailResultDTO {
    return DeleteCocktailResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 상품입니다."
    )
}