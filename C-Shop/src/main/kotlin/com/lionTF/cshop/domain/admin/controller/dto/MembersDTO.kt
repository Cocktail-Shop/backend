package com.lionTF.cshop.domain.admin.controller.dto

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class MembersSearchDTO(
    val httpStatus: Int,
    val message: String,
    val keyword: String? = null,
    val result: Page<MembersDTO>? = null
) {
    companion object {
        fun memberToResponseMemberSearchPageDTO(
            findMemberDTO: Page<MembersDTO>,
            keyword: String? = ""
        ): MembersSearchDTO {
            return MembersSearchDTO(
                HttpStatus.OK.value(),
                "회원 조회를 성공했습니다.",
                keyword,
                findMemberDTO
            )
        }
    }

}

data class MembersDTO(
    val memberId: Long,
    val id: String,
    val address: String,
    val memberName: String,
    val phoneNumber: String,
    val createdAt: LocalDateTime
)
