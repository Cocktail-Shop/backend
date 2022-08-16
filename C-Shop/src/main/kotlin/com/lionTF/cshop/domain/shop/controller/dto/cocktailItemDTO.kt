package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.CocktailItem

//칵테일 단건 조회시 칵테일에 포함되어 있는 아이템 정보를 담기위한 dto
data class CocktailItemDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
){
    companion object{
        //칵테일 단건 조회시, cocktailItems에 들어갈 아이템 정보들을 dto로 변환해주는 메소드
        fun fromCocktailItem(cocktailItem: CocktailItem): CocktailItemDTO {
            val item =CocktailItemInfoDTO.fromItem(cocktailItem.item)
            return CocktailItemDTO(
                itemId = item.itemId,
                itemName = item.itemName,
                price = item.price,
                amount = item.amount,
            )
        }
    }
}
