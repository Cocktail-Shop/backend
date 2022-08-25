package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Category

class CocktailWishListDTO(
    val cocktailWishListId: Long = 0,
    val memberId: Long = 0,
    val cocktailId: Long = 0,
    val cocktailName: String = "",
    val cocktailImgUrl: String = "",
)
