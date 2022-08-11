package com.lionTF.CShop.domain.member.controller.dto

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


data class RequestSignUpDTO(var id:String="",
                            var password:String="",
                            var phoneNumber:String="",
                            var memberName:String="",
                            var address:String="",
                            var detailAddress:String=""){


        private val passwordEncoder: PasswordEncoder=BCryptPasswordEncoder()


        fun encoding(){
            this.password=passwordEncoder.encode(password)
        }
        companion object{
            fun toFormDTO():RequestSignUpDTO{
                return RequestSignUpDTO()
            }
        }

}
