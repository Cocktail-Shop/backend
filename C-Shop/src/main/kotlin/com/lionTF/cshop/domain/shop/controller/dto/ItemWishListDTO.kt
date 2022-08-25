package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Category

class ItemWishListDTO(
    val itemWishListId: Long = 0,
    val memberId: Long = 0,
    val itemId: Long = 0,
    val category: Category = Category.ALCOHOL,
    val itemName: String = "",
    val itemImgUrl: String = "",
    val price: Int = 0
)
