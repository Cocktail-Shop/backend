package com.lionTF.cshop.domain.member.controller.dto

data class PreMemberInfoRequestDTO(
    var phoneNumber: String = "",
    var address: String = "",
    var detailAddress: String = ""
) {
    companion object {
        fun toFormDTO(): PreMemberInfoRequestDTO {
            return PreMemberInfoRequestDTO()
        }
    }
}
