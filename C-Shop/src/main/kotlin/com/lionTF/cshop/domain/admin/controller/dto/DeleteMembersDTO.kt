package com.lionTF.cshop.domain.admin.controller.dto

data class DeleteMembersDTO (
    var memberIds: MutableList<Long> = mutableListOf()
)