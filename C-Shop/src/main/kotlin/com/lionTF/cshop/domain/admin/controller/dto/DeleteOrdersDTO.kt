package com.lionTF.cshop.domain.admin.controller.dto

data class DeleteOrdersDTO (
    var orderIds: MutableList<Long> = mutableListOf()
)