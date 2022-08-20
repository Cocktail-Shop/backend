package com.lionTF.cshop.domain.member.controller.dto

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.http.HttpStatus

data class ResponseMyPageDTO(
    val status: Int,
    val message: String,
    val result: MyPageResultDTO,
) {

    companion object {
        fun fromMember(member: Member): ResponseMyPageDTO {
            val myPageResultDTO = MyPageResultDTO(
                member.id, member.phoneNumber, member.memberName, member.address, member.detailAddress
            )

            return ResponseMyPageDTO(
                HttpStatus.OK.value(), message = "마이페이지 조회를 성공했습니다.", myPageResultDTO
            )
        }
    }

}

data class RequestUpdateMyPageDTO(
    var id: String = "", var address: String = "", var detailAddress: String = ""
) {
    companion object {
        fun toFormDTO(responseMyPageDTO: ResponseMyPageDTO): RequestUpdateMyPageDTO {
            return RequestUpdateMyPageDTO(
                id = responseMyPageDTO.result.id,
                address = responseMyPageDTO.result.address,
                detailAddress = responseMyPageDTO.result.detailAddress
            )
        }
    }
}

data class MyPageResultDTO(
    val id: String,
    val phoneNumber: String,
    val memberName: String,
    val address: String,
    val detailAddress: String
)

data class RequestUpdatePasswordDTO(var pastPassword: String = "", var newPassword: String = "") {
    companion object {
        fun toFormDTO(): RequestUpdatePasswordDTO {
            return RequestUpdatePasswordDTO()
        }
    }
}
