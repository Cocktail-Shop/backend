package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Cocktail

data class SearchCocktailResultDTO(
    val status: Int,
    val message: String,
    val result: SearchCocktailDTO
)

data class SearchCocktailDTO(
    val keyword: String,
    val data: List<SearchCocktailInfoDTO>
)

data class SearchCocktailInfoDTO(
    val cocktailId: Long,
    val cocktailName: String,
    val cocktailImgUrl: String
){
    companion object{
        fun fromCocktail(cocktail: Cocktail) : SearchCocktailInfoDTO{
            return SearchCocktailInfoDTO(
                cocktailId = cocktail.cocktailId,
                cocktailName = cocktail.cocktailName,
                cocktailImgUrl = cocktail.cocktailImgUrl,
            )
        }
    }
}


