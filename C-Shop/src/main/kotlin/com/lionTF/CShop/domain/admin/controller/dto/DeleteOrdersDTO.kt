package com.lionTF.CShop.domain.admin.controller.dto

data class DeleteOrdersDTO (
    var orderIds: MutableList<Long> = mutableListOf()
)