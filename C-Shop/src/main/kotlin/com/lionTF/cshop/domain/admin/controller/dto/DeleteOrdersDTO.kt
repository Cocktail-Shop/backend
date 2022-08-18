package com.lionTF.cshop.domain.admin.controller.dto

data class DeleteOrdersDTO (
    val orderIds: MutableList<Long> = mutableListOf()
)
