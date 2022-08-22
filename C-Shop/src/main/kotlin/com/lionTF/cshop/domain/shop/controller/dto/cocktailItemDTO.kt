package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.CocktailItem

data class CocktailItemDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
) {

    companion object {
        fun fromCocktailItem(cocktailItem: CocktailItem): CocktailItemDTO {
            val item = CocktailItemInfoDTO.fromItem(cocktailItem.item)
            return CocktailItemDTO(
                itemId = item.itemId,
                itemName = item.itemName,
                price = item.price,
                amount = item.amount,
            )
        }
    }
}
