package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.http.HttpStatus

data class ResponseCocktailDTO (
    val httpStatus: Int,
    val message: String,
    val result: CocktailResultDTOAddItemIds? = null,
){
    companion object{
        fun cocktailToResponseCocktailPageDTO(cocktailResultDTOAddItemIds: CocktailResultDTOAddItemIds): ResponseCocktailDTO {
            return ResponseCocktailDTO(
                HttpStatus.OK.value(),
                "칵테일 조회를 성공했습니다.",
                cocktailResultDTOAddItemIds
            )
        }
    }
}

data class CocktailResultDTO(
    var cocktailName: String,
    var cocktailDescription: String
)

data class CocktailResultDTOAddItemIds(
    var cocktailName: String,
    var cocktailDescription: String,
    var itemIds: MutableList<Long>
){
    companion object {
        fun addItemIds(cocktailResultDTO: CocktailResultDTO, itemIds: MutableList<Long>): CocktailResultDTOAddItemIds {
            return CocktailResultDTOAddItemIds(
                cocktailName = cocktailResultDTO.cocktailName,
                cocktailDescription = cocktailResultDTO.cocktailDescription,
                itemIds = itemIds
            )
        }
    }
}

data class RequestUpdateCocktailDTO(
    var cocktailName: String,
    var cocktailDescription: String,
    var itemIds: MutableList<Long> = mutableListOf()
){
    companion object{
        fun formDTOFromResponseCocktailDTO(responseCocktailDTO: ResponseCocktailDTO, itemIds: MutableList<Long>): RequestUpdateCocktailDTO {
            return RequestUpdateCocktailDTO(
                cocktailName = responseCocktailDTO.result!!.cocktailName,
                cocktailDescription = responseCocktailDTO.result.cocktailDescription,
                itemIds = itemIds
            )
        }
    }
}
