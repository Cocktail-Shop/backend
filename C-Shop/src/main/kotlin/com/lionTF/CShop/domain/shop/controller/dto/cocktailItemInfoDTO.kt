package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Item

//칵테일 단건 조회시 칵테일에 포함되어 있는 상품들의 정보를 담는 dto
data class CocktailItemInfoDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
){
    companion object{
        //CocktailItem entity에서 toReadCocktailItemDTO()를 수행시켜주기 위한 메소드
        fun itemToCocktailItemInfoDTO(item: Item): CocktailItemInfoDTO {
            return CocktailItemInfoDTO(
                itemId = item.itemId,
                itemName = item.itemName,
                price = item.price,
                amount = item.amount,
            )
        }
    }
}

