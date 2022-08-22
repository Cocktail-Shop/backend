package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.shop.models.Cart

data class CartItemDTO(
    val item: Item,
    val cart: Cart,
    val amount: Int,
)
