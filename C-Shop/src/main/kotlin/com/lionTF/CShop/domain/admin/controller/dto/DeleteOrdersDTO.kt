package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.http.HttpStatus

class DeleteOrdersDTO (
    var orderIds: MutableList<Long> = mutableListOf()
)

// 상태코드와 message를 반환하기 위한 DTO
data class DeleteOrdersResultDTO(
    val status: Int,
    val message: String,
)

// 삭제 성공시 reposneBody에 저장되는 함수
fun setDeleteSuccessOrdersResultDTO(): DeleteOrdersResultDTO {
    return DeleteOrdersResultDTO(
        status = HttpStatus.NO_CONTENT.value(),
        message = "상품이 삭제되었습니다."
    )
}

// 삭제 실패시 reposneBody에 저장되는 함수
fun setDeleteFailOrdersResultDTO(): DeleteOrdersResultDTO {
    return DeleteOrdersResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 상품입니다."
    )
}