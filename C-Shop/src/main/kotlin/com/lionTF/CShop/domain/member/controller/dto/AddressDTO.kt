package com.lionTF.CShop.domain.member.controller.dto

import com.lionTF.CShop.domain.member.models.Member

data class AddressDTO(
    var Address: String = "",
    var AddressDetail:String="",
){
    companion object{
        fun memberToAddressDTO(member: Member) : AddressDTO{
            return AddressDTO(member.address, member.detailAddress)
        }
    }
}
