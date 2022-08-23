package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Cocktail

data class SearchCocktailInfoDTO(
    val cocktailId: Long,
    val cocktailName: String,
    val cocktailImgUrl: String
) {

    companion object {
        fun fromCocktail(cocktail: Cocktail): SearchCocktailInfoDTO {
            return SearchCocktailInfoDTO(
                cocktailId = cocktail.cocktailId,
                cocktailName = cocktail.cocktailName,
                cocktailImgUrl = cocktail.cocktailImgUrl,
            )
        }
    }
}
