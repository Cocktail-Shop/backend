package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.admin.models.Category
import java.time.LocalDateTime

data class FindItemDTO(
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,
    var category: Category,
    var createdAt: LocalDateTime,
)
