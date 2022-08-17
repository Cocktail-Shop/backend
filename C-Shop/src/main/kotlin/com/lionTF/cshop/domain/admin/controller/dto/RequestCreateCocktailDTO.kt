package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.web.multipart.MultipartFile

data class RequestCreateCocktailDTO(
    val cocktailName: String = "",
    val cocktailDescription: String = "",
    val itemIds: MutableList<Long> = mutableListOf(),
    val category: Category = Category.COCKTAIL,
    val cocktailImgUrl: MultipartFile? = null,

    ) {
    companion object {
        fun toFormDTO(): RequestCreateCocktailDTO {
            return RequestCreateCocktailDTO()
        }
    }
}
