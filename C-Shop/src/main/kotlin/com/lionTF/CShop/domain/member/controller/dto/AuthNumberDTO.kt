package com.lionTF.CShop.domain.member.controller.dto

data class RequestAuthNumberDTO (val email:String)

data class RequestVerifyAuthNumberDTO(val email: String,val authNumber:String)