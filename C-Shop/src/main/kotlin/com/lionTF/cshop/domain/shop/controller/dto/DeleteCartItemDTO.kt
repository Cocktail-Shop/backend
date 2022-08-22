package com.lionTF.cshop.domain.shop.controller.dto

data class DeleteCartItemDTO(
    val memberId: Long?,
    val cartItemId: Long,
    val itemIds: MutableList<Long> = mutableListOf(),
)

data class DeleteCartItemRequestDTO(
    val cartItemID: Long,
    val itemIds: MutableList<Long> = mutableListOf(),
)
