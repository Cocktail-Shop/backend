package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Cocktail
import org.springframework.http.HttpStatus

// 칵테일 단건 조회시 칵테일 정보를 담는 dto
data class CocktailDTO(
    val cocktailId : Long,
    val cocktailName: String,
    var cocktailDescription: String,
    var cocktailImgUrl: String,
    var cocktailStatus: Boolean,
    var cocktailItems: List<CocktailItemDTO>
){
    companion object{
        //칵테일 단건 조회 result부분에 들어갈 정보들을 dto로 변환
        fun cocktailToCocktailDTO(cocktail: Cocktail): CocktailDTO {
            val itemList = cocktail.cocktailItem
            val dtoList: MutableList<CocktailItemDTO> = mutableListOf()
            for(item in itemList!!){
                dtoList.add(CocktailItemDTO.cocktailItemToCocktailItemDTO(item))

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

//칵테일 단건 조회시 최종 응답 형태를 맞춰주기 위한 dto
data class CocktailResultDTO(
    val status: Int,
    val message: String,
    val result: CocktailDTO
){
    companion object{
        //칵테일 단건 조회 응답 최종 형태 dto를 만들어주는 메소드
        fun setCocktailResultDTO(result: CocktailDTO): CocktailResultDTO {
            return CocktailResultDTO(
                status = HttpStatus.OK.value(),
                message = "칵테일 단건 조회 성공",
                result = result
            )
        }
    }

}



