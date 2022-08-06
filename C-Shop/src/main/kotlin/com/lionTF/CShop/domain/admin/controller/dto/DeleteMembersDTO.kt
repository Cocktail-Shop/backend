package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.http.HttpStatus

class DeleteMembersDTO (
    var memberIds: MutableList<Long> = mutableListOf()
)

// 상태코드와 message를 반환하기 위한 DTO
data class DeleteMembersResultDTO(
    val status: Int,
    val message: String,
)

// 삭제 성공시 reposneBody에 저장되는 함수
fun setDeleteSuccessMembersResultDTO(): DeleteMembersResultDTO {
    return DeleteMembersResultDTO(
        status = HttpStatus.NO_CONTENT.value(),
        message = "회원이 삭제되었습니다."
    )
}

// 삭제 실패시 reposneBody에 저장되는 함수
fun setDeleteFailMembersResultDTO(): DeleteMembersResultDTO {
    return DeleteMembersResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 회원입니다."
    )
}