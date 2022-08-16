package com.lionTF.cshop.domain.admin.controller.dto


// 삭제할 상품의 ID 정보
data class DeleteCocktailDTO(
    var cocktailIds: MutableList<Long> = mutableListOf()
)
