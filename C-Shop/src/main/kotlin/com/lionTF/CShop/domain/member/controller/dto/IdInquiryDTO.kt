package com.lionTF.CShop.domain.member.controller.dto




data class IdInquiryResultDTO(val id:String)

data class RequestIdInquiryDTO(val memberName:String,val phoneNumber:String)

data class ResponseIdInquiryDTO(val status:Int,val message:String,val result: IdInquiryResultDTO){
    companion object{
        fun memberToResponseIdInquiryDTO(status:Int,message:String,id:String): ResponseIdInquiryDTO {
                return ResponseIdInquiryDTO(
                    status,
                    message,
                    IdInquiryResultDTO(id)
                )
        }
    }
}
