package com.lionTF.CShop.domain.admin.controller.dto

data class DeleteMembersDTO (
    var memberIds: MutableList<Long> = mutableListOf()
)