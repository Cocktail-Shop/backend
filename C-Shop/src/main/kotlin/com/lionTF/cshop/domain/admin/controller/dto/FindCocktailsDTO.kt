package com.lionTF.cshop.domain.admin.controller.dto

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ResponseSearchCocktailSearchDTO(
    val httpStatus: Int,
    val message: String,
    val keyword: String? = null,
    val result: Page<FindCocktailsDTO>? = null,
){
    companion object{
        fun cocktailToResponseCocktailSearchPageDTO(findCocktailDTO: Page<FindCocktailsDTO>, keyword: String?): ResponseSearchCocktailSearchDTO {
            return ResponseSearchCocktailSearchDTO(
                HttpStatus.OK.value(),
                "칵테일 조회를 성공했습니다.",
                keyword,
                findCocktailDTO
            )
        }
    }
}

data class FindCocktailsDTO(
    var cocktailId: Long,
    var cocktailName: String,
    var cocktailDescription: String,
    var createdAt: LocalDateTime
)
