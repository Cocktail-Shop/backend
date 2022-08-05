package com.lionTF.CShop.domain.admin.controller.dto

data class FindMembersDTO(
    val id: String,
    val address: String,
    val memberName: String,
    val phoneNumber: String
)

data class FindMembersResultDTO(
    val httpStatus: Int,
    val message: String,
    val findMembersDTO: List<FindMembersDTO>
)
