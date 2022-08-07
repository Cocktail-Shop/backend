package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.data.domain.Page

data class FindMembersDTO (
    val memberId: Long,
    val id: String,
    val address: String,
    val memberName: String,
    val phoneNumber: String,
)

data class FindMembersResultDTO(
    val httpStatus: Int,
    val message: String,
    val findMembersDTO: Page<FindMembersDTO>
)
