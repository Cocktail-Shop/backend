package com.lionTF.cshop.domain.member.controller.dto

import com.lionTF.cshop.domain.member.models.Member

data class AddressDTO(
    val Address: String = "",
    val AddressDetail:String="",
){
    companion object{
        fun fromMember(member: Member) : AddressDTO{
            return AddressDTO(member.address, member.detailAddress)
        }
    }
}
