package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.domain.admin.models.Item
import org.springframework.http.HttpStatus

data class CreateCocktailDTO(
    var cocktailName: String = "",
    var cocktailDescription: String ="",
    var itemIds: MutableList<Long> = mutableListOf(),
    var category: Category = Category.COCKTAIL,
)


data class CreateCocktailResultDTO(
    val status: Int,
    val message: String,
    val cocktailId: Long,
)

fun cocktailDTOToCockTail(createCocktailDTO: CreateCocktailDTO): Cocktail {
    return Cocktail(
        cocktailName = createCocktailDTO.cocktailName,
        cocktailDescription = createCocktailDTO.cocktailDescription,
        category = createCocktailDTO.category,
    )
}

fun cocktailItemToCocktailItemDTO(itemId: Item, cocktailId: Cocktail): CocktailItem {
    return CocktailItem(
        item = itemId,
        cocktail = cocktailId,
    )
}

// 등록 성공시 reposneBody에 저장되는 함수
fun setCreateSuccessCocktailResultDTO(cocktailId: Long): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "칵테일 상품이 등록되었습니다.",
        cocktailId = cocktailId
    )
}

// 등록 실패시 reposneBody에 저장되는 함수
fun setCreateFailCocktailResultDTO(): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "이미 존재하는 칵테일 상품입니다.",
        cocktailId = 0L
    )
}

// 존재하지 않는 Item일 때 reposneBody에 저장되는 함수
fun failToNoContentItemResultDTO(): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 상품입니다.",
        cocktailId = 0L
    )
}

// 수정 성공시 reposneBody에 저장되는 함수
fun setUpdateSuccessCocktailResultDTO(cocktailId: Long): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "칵테일 상품이 수정되었습니다.",
        cocktailId = cocktailId
    )
}

// 수정 실패시 reposneBody에 저장되는 함수
fun setUpdateFailCocktailResultDTO(): CreateCocktailResultDTO {
    return CreateCocktailResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 칵테일 상품입니다.",
        cocktailId = 0L
    )
}