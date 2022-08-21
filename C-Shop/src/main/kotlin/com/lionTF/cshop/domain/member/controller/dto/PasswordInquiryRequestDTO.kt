package com.lionTF.cshop.domain.member.controller.dto

data class PasswordInquiryRequestDTO(var id: String = "", var email: String = "") {
    companion object {
        fun toFormDTO(): PasswordInquiryRequestDTO {
            return PasswordInquiryRequestDTO()
        }
    }
}
