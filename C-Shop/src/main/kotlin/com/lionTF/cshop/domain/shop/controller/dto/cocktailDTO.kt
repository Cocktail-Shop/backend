package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Cocktail
import org.springframework.http.HttpStatus

data class CocktailDTO(
    val cocktailId: Long,
    val cocktailName: String,
    var cocktailDescription: String,
    var cocktailImgUrl: String,
    var cocktailStatus: Boolean,
    var cocktailItems: List<CocktailItemDTO>
) {

    companion object {
        fun fromCocktail(cocktail: Cocktail): CocktailDTO {
            val itemList = cocktail.cocktailItem
            val dtoList: MutableList<CocktailItemDTO> = mutableListOf()
            for (item in itemList!!) {
                dtoList.add(CocktailItemDTO.fromCocktailItem(item))
            }
            return CocktailDTO(
                cocktailId = cocktail.cocktailId,
                cocktailName = cocktail.cocktailName,
                cocktailDescription = cocktail.cocktailDescription,
                cocktailImgUrl = cocktail.cocktailImgUrl,
                cocktailStatus = cocktail.cocktailStatus,
                cocktailItems = dtoList
            )
        }
    }
}

data class CocktailResultDTO(
    val status: Int,
    val message: String,
    val result: CocktailDTO
) {

    companion object {
        fun setCocktailResultDTO(result: CocktailDTO): CocktailResultDTO {
            return CocktailResultDTO(
                status = HttpStatus.OK.value(),
                message = "칵테일 단건 조회 성공",
                result = result
            )
        }
    }
}
