package com.lionTF.CShop.domain.admin.controller.dto

// 삭제할 상품의 ID 정보
data class DeleteItemDTO(
    var itemIds: MutableList<Long> = mutableListOf()
)
