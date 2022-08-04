package com.lionTF.CShop.domain.member.dto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


class SignUpDTO{
    data class RequestDTO(
        val id:String,
        var password:String,
        val phoneNumber:String,
        val memberName:String,
        val address:String,
    ) {

        val passwordEncoder: PasswordEncoder=BCryptPasswordEncoder()

        init {
            password=passwordEncoder.encode(password)
        }
    }
}
