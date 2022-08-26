package com.lionTF.cshop.global.controller.dto

import com.lionTF.cshop.domain.member.controller.dto.MemberResponseDTO
import org.springframework.http.HttpStatus

data class ErrorMessageDTO(
    val status: Int,
    val message: String,
    var href: String = ""
) {
    companion object{
        fun toBasicErrorMessageDTO(status: Int): MemberResponseDTO {
            return when (status) {
                HttpStatus.INTERNAL_SERVER_ERROR.value() -> MemberResponseDTO(status, "서버 에러 입니다.","/items")
                else -> MemberResponseDTO(status, "잘못된 접근 입니다.", "/items")
            }
        }
    }
}
