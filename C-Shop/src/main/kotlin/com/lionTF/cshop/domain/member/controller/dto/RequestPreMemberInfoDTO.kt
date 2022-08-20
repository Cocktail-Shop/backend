package com.lionTF.cshop.domain.member.controller.dto

data class RequestPreMemberInfoDTO(
    var phoneNumber: String = "",
    var address: String = "",
    var detailAddress: String = ""
) {
    companion object {
        fun toFormDTO(): RequestPreMemberInfoDTO {
            return RequestPreMemberInfoDTO()
        }
    }
}
