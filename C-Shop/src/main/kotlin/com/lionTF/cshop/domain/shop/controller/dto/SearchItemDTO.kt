package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Item

data class SearchItemInfoDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val degree: Int,
    val itemImgUrl: String
) {
    companion object {
        fun fromItem(item: Item): SearchItemInfoDTO {
            return SearchItemInfoDTO(
                itemId = item.itemId,
                itemName = item.itemName,
                price = item.price,
                degree = item.degree,
                itemImgUrl = item.itemImgUrl
            )
        }
    }
}
