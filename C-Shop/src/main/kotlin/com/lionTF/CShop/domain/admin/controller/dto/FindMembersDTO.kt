package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.data.domain.Page

data class FindMembersDTO (
    var memberId: Long,
    var id: String,
    var address: String,
    var memberName: String,
    var phoneNumber: String,
)

data class FindMembersResultDTO(
    var httpStatus: Int,
    var message: String,
    var findMembersDTO: Page<FindMembersDTO>
)
