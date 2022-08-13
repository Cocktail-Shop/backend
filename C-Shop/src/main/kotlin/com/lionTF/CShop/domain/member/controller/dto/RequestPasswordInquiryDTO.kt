package com.lionTF.CShop.domain.member.controller.dto

data class RequestPasswordInquiryDTO (var id:String,var email:String){
    companion object{
        fun toFormDTO():RequestPasswordInquiryDTO{
            return RequestPasswordInquiryDTO("","")
        }
    }
}