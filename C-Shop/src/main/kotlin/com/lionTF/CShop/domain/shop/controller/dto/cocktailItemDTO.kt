package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.global.HttpStatus


//칵테일 단건 조회시 칵테일에 포함되어 있는 아이템 정보를 담기위한 dto
data class ReadCocktailItemDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
)


