package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.global.HttpStatus

// 삭제할 상품의 ID 정보
data class DeleteItemDTO(
    var itemIds: MutableList<Long> = mutableListOf<Long>()
)

// 상태코드와 message를 반환하기 위한 DTO
data class DeleteItemResultDTO(
    val status: Int,
    val message: String,
)

// 삭제 성공시 reposneBody에 저장되는 함수
fun setDeleteSuccessItemResultDTO(): DeleteItemResultDTO {
    return DeleteItemResultDTO(
        status = HttpStatus.NoContent.code,
        message = "상품이 삭제되었습니다."
    )
}

// 삭제 실패시 reposneBody에 저장되는 함수
fun setDeleteFailItemResultDTO(): DeleteItemResultDTO {
    return DeleteItemResultDTO(
        status = HttpStatus.InternalServerError.code,
        message = "존재하지 않는 상품입니다."
    )
}