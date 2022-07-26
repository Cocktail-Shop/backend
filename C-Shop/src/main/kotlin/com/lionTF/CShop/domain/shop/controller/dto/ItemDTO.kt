package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Category


data class ReadItemDTO (
    val itemId: Long,
    val price: Int,
    val amount: Int,
    val degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,
    var category: Category,
    var itemStatus: Boolean,
){

}