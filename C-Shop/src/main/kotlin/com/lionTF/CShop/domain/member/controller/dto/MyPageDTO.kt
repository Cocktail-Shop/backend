package com.lionTF.CShop.domain.member.controller.dto

import com.lionTF.CShop.domain.member.models.Member
import org.springframework.http.HttpStatus

data class ResponseMyPageDTO(val status:Int,
                             val message:String,
                             val result:MyPageResultDTO,
){

    companion object{
        fun memberToResponseMyPageDTO(member:Member): ResponseMyPageDTO {
            val myPageResultDTO=MyPageResultDTO(
                member.id,
                member.phoneNumber,
                member.memberName,
                member.address
            )

            return ResponseMyPageDTO(
                HttpStatus.OK.value(),
                message="마이페이지 조회를 성공했습니다.",
                myPageResultDTO
            )
        }
    }

}

data class RequestUpdateMyPageDTO(val id:String,val address: String)

data class MyPageResultDTO(val id:String,val phoneNumber: String,val memberName:String,val address:String)

data class RequestUpdatePasswordDTO(val pastPassword:String,val newPassword:String)