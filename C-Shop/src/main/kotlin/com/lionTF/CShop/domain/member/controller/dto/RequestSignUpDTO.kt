package com.lionTF.CShop.domain.member.controller.dto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


data class RequestSignUpDTO(val id:String,
                            var password:String,
                            val phoneNumber:String,
                            val memberName:String,
                            val address:String,){


        val passwordEncoder: PasswordEncoder=BCryptPasswordEncoder()

        init {
            password=passwordEncoder.encode(password)
        }

}
