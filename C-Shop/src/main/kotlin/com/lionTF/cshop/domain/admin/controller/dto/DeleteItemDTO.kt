package com.lionTF.cshop.domain.admin.controller.dto

data class DeleteItemDTO(
    val itemIds: MutableList<Long> = mutableListOf()
)
