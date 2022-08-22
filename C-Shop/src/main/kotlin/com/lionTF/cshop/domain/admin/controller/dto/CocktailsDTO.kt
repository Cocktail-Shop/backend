package com.lionTF.cshop.domain.admin.controller.dto

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class CocktailsSearchDTO(
    val httpStatus: Int,
    val message: String,
    val cocktailName: String? = null,
    val result: Page<CocktailsDTO>? = null,
) {
    companion object {
        fun cocktailToResponseCocktailSearchPageDTO(
            findCocktailDTO: Page<CocktailsDTO>,
            cocktailName: String? = ""
        ): CocktailsSearchDTO {
            return CocktailsSearchDTO(
                HttpStatus.OK.value(),
                "칵테일 조회를 성공했습니다.",
                cocktailName,
                findCocktailDTO
            )
        }
    }
}

data class CocktailsDTO(
    val cocktailId: Long,
    val cocktailName: String,
    val cocktailDescription: String,
    val cocktailImgUrl: String,
    val createdAt: LocalDateTime
)
