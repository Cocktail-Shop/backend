package com.lionTF.cshop.domain.member.controller.dto

data class AuthNumberRequestDTO(
    val email: String
)

data class AuthNumberVerifyRequestDTO(
    val email: String,
    val authNumber: String
)
