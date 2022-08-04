package com.lionTF.CShop.domain.member.dto





class IdInquiryDTO{

    data class ResultDTO(val id:String)

    data class RequestDTO(val memberName:String,val phoneNumber:String)

    data class ResponseDTO(val status:Int,val message:String,val result:ResultDTO)

}