package com.lionTF.cshop.domain.admin.controller.dto

import org.springframework.http.HttpStatus

data class CocktailResponseDTO(
    val httpStatus: Int,
    val message: String,
    val result: CocktailResultDTOAddItemIds? = null,
) {
    companion object {
        fun cocktailToResponseCocktailPageDTO(cocktailResultDTOAddItemIds: CocktailResultDTOAddItemIds): CocktailResponseDTO {
            return CocktailResponseDTO(
                HttpStatus.OK.value(),
                "칵테일 조회를 성공했습니다.",
                cocktailResultDTOAddItemIds
            )
        }
    }
}

data class CocktailResultDTO(
    val cocktailName: String,
    val cocktailDescription: String,
    val cocktailImgUrl: String,
)

data class CocktailResultDTOAddItemIds(
    val cocktailName: String,
    val cocktailDescription: String,
    val cocktailImgUrl: String,
    val itemIds: MutableList<Long>
) {
    companion object {
        fun addItemIds(cocktailResultDTO: CocktailResultDTO, itemIds: MutableList<Long>): CocktailResultDTOAddItemIds {
            return CocktailResultDTOAddItemIds(
                cocktailName = cocktailResultDTO.cocktailName,
                cocktailDescription = cocktailResultDTO.cocktailDescription,
                cocktailImgUrl = cocktailResultDTO.cocktailImgUrl,
                itemIds = itemIds
            )
        }
    }
}

data class CocktailUpdateRequestDTO(
    val cocktailName: String,
    val cocktailDescription: String,
    val cocktailImgUrl: String,
    val itemIds: MutableList<Long> = mutableListOf()
) {
    companion object {
        fun formDTOFromResponseCocktailDTO(
            responseCocktailDTO: CocktailResponseDTO,
            itemIds: MutableList<Long>
        ): CocktailUpdateRequestDTO? {
            return responseCocktailDTO.result?.let {
                CocktailUpdateRequestDTO(
                    cocktailName = it.cocktailName,
                    cocktailDescription = responseCocktailDTO.result.cocktailDescription,
                    cocktailImgUrl = responseCocktailDTO.result.cocktailImgUrl,
                    itemIds = itemIds
                )
            }
        }
    }
}
