package com.lionTF.CShop.domain.admin.controller.dto

// 삭제할 장바구니 상품의 ID 정보
data class DeleteCartItemDTO(
    var itemIds: MutableList<Long> = mutableListOf()
)
