package com.lionTF.cshop.domain.member.controller.dto

import org.springframework.security.crypto.password.PasswordEncoder


data class SignUpRequestDTO(
    var id: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var memberName: String = "",
    var address: String = "",
    var email: String = "",
    var detailAddress: String = ""
) {

    fun encoding(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(password)
    }
    companion object {
        fun toFormDTO(): SignUpRequestDTO {
            return SignUpRequestDTO()
        }
    }
}
