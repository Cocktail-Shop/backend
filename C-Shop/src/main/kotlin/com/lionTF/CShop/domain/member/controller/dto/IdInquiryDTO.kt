package com.lionTF.CShop.domain.member.controller.dto

import com.lionTF.CShop.domain.member.models.Member
import org.springframework.http.HttpStatus


data class IdInquiryResultDTO(val id:String)

data class RequestIdInquiryDTO(var memberName:String,var phoneNumber:String){

    companion object{
        fun toFormDTO():RequestIdInquiryDTO{
            return RequestIdInquiryDTO("","")
        }
    }
}

data class ResponseIdInquiryDTO(val status:Int,val message:String,val result: IdInquiryResultDTO){
    companion object{
        fun memberToSuccessResponseIdInquiryDTO(member: Member): ResponseIdInquiryDTO {
                return ResponseIdInquiryDTO(
                    HttpStatus.OK.value(),
                    "회원아이디를 찾았습니다.",
                    IdInquiryResultDTO(member.id)
                )
        }
    }
}
