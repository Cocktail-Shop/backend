package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus

data class ResponseSearchMembersResultDTO(
    var httpStatus: Int,
    var message: String,
    val keyword: String? = null,
    var result: Page<FindMembersDTO>? = null
){
    companion object{
        fun memberToResponseMemberSearchPageDTO(findMemberDTO: Page<FindMembersDTO>, keyword: String?): ResponseSearchMembersResultDTO {
            return ResponseSearchMembersResultDTO(
                HttpStatus.OK.value(),
                "상품 조회를 성공했습니다.",
                keyword,
                findMemberDTO
            )
        }
    }

}

data class FindMembersDTO (
    var memberId: Long,
    var id: String,
    var address: String,
    var memberName: String,
    var phoneNumber: String,
)
