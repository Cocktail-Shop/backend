package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Item

data class CocktailItemInfoDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
) {

    companion object {
        fun fromItem(item: Item): CocktailItemInfoDTO {
            return CocktailItemInfoDTO(
                itemId = item.itemId,
                itemName = item.itemName,
                price = item.price,
                amount = item.amount,
            )
        }
    }
}
