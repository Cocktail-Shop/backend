package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.shop.models.Orders

data class OrderItemDTO(
    var orders: Orders,
    var item: Item,
    var amount: Int,
)
