package com.lionTF.CShop.domain.admin.controller.dto

data class ResponseItemDTO(
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,
    var createdAt: String,
)
