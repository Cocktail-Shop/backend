package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.shop.models.Orders

data class OrderItemDTO(
    var orders: Orders,
    var item: Item,
    var amount: Int,
)
