package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.global.HttpStatus

// 칵테일 단건 조회시 칵테일 정보를 담는 dto
data class ReadCocktailDTO(
    val cocktailId : Long,
    val cocktailName: String,
    var cocktailDescription: String,
    var cocktailImgUrl: String,
    var cocktailStatus: Boolean,
    var cocktailItems: List<ReadCocktailItemDTO>
)

//칵테일 단건 조회시 최종 응답 형태를 맞춰주기 위한 dto
data class ReadCocktailResultDTO(
    val status: HttpStatus,
    val message: String,
    val result: ReadCocktailDTO
)