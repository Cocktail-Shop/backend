package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Item

data class SearchItemResultDTO(
    val status: Int,
    val message: String,
    val result: SearchItemDTO
)

data class SearchItemDTO(
    val keyword: String,
    val data: List<SearchItemInfoDTO>
)

data class SearchItemInfoDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val degree: Int,
    val itemImgUrl: String
)

fun itemToSearchIteminfoDTO(item: Item) : SearchItemInfoDTO{
    return SearchItemInfoDTO(
        itemId = item.itemId,
        itemName = item.itemName,
        price = item.price,
        degree = item.degree,
        itemImgUrl = item.itemImgUrl
    )
}