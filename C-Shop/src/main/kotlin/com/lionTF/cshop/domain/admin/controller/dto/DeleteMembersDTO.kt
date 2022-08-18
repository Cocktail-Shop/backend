package com.lionTF.cshop.domain.admin.controller.dto

data class DeleteMembersDTO (
    val memberIds: MutableList<Long> = mutableListOf()
)
