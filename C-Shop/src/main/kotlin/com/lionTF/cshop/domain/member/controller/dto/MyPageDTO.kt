package com.lionTF.cshop.domain.member.controller.dto

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.http.HttpStatus

data class MyPageResponseDTO(
    val status: Int,
    val message: String,
    val result: MyPageResultDTO,
) {

    companion object {
        fun fromMember(member: Member): MyPageResponseDTO {
            val myPageResultDTO = MyPageResultDTO(
                member.id, member.phoneNumber, member.memberName, member.address, member.detailAddress
            )

            return MyPageResponseDTO(
                HttpStatus.OK.value(), message = "마이페이지 조회를 성공했습니다.", myPageResultDTO
            )
        }
    }

}

data class RequestUpdateMyPageDTO(
    var id: String = "", var address: String = "", var detailAddress: String = ""
) {
    companion object {
        fun toFormDTO(myPageResponseDTO: MyPageResponseDTO): RequestUpdateMyPageDTO {
            return RequestUpdateMyPageDTO(
                id = myPageResponseDTO.result.id,
                address = myPageResponseDTO.result.address,
                detailAddress = myPageResponseDTO.result.detailAddress
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

data class PasswordUpdateRequestDTO(var pastPassword: String = "", var newPassword: String = "") {
    companion object {
        fun toFormDTO(): PasswordUpdateRequestDTO {
            return PasswordUpdateRequestDTO()
        }
    }
}
