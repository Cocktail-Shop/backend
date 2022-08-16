package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category

// 상품 등록을 위한 정보가 실려오는 JSON 형태를 DB에 저장하기 위한 dto
data class RequestCreateCocktailDTO(
    var cocktailName: String = "",
    var cocktailDescription: String ="",
    var itemIds: MutableList<Long> = mutableListOf(),
    var category: Category = Category.COCKTAIL,

) {
    companion object {
        fun toFormDTO(): RequestCreateCocktailDTO {
            return RequestCreateCocktailDTO()
        }
    }
}