package com.lionTF.cshop.domain.member.controller.dto

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.http.HttpStatus


data class IdInquiryResultDTO(
    val id: String
)

data class IdInquiryRequestDTO(
    var memberName: String = "",
    var email: String = ""
) {
    companion object {
        fun toFormDTO(): IdInquiryRequestDTO {
            return IdInquiryRequestDTO()
        }
    }
}

data class IdInquiryResponseDTO(
    val status: Int,
    val message: String,
    val result: IdInquiryResultDTO
) {
    companion object {
        fun toSuccessIdInquiryResponseDTO(member: Member): IdInquiryResponseDTO {
            return IdInquiryResponseDTO(
                HttpStatus.OK.value(),
                "회원아이디를 찾았습니다.",
                IdInquiryResultDTO(member.id)
            )
        }
    }
}
