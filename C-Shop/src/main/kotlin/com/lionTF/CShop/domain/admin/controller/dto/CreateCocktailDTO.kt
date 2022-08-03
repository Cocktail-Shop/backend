package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.domain.admin.models.Item
import org.springframework.http.HttpStatus

data class CreateCocktailDTO(
    val cocktailName: String,
    val cocktailDescription: String,
    val itemIds: MutableList<Long>,
)


data class CreateCocktailResultDTO(
    val status: Int,
    val message: String,
)

fun cocktailToCockTailDTO(createCocktailDTO: CreateCocktailDTO): Cocktail {

    return Cocktail(
        cocktailName = createCocktailDTO.cocktailName,
        cocktailDescription = createCocktailDTO.cocktailDescription,
    )
}

fun cocktailItemToCocktailItemDTO(itemId: Item, cocktailId: Cocktail): CocktailItem {

    return CocktailItem(
        item = itemId,
        cocktail = cocktailId,
    )
}

// 등록 성공시 reposneBody에 저장되는 함수
fun setCreateSuccessCocktailResultDTO(): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "칵테일 상품이 등록되었습니다."
    )
}

// 등록 실패시 reposneBody에 저장되는 함수
fun setCreateFailCocktailResultDTO(): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "이미 존재하는 칵테일 상품입니다."
    )
}

// 존재하지 않는 Item일 때 reposneBody에 저장되는 함수
fun FailToNoContentItemResultDTO(): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 상품입니다."
    )
}