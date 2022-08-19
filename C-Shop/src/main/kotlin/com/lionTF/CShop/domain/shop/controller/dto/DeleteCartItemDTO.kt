package com.lionTF.CShop.domain.admin.controller.dto

// 삭제할 장바구니 상품의 ID 정보
data class DeleteCartItemDTO(
        val memberId: Long?,
        val cartItemId: Long,
        val itemIds: MutableList<Long> = mutableListOf(),
)

data class DeleteCartItemRequestDTO(
        val cartItemID: Long,
        val itemIds: MutableList<Long> = mutableListOf(),
)