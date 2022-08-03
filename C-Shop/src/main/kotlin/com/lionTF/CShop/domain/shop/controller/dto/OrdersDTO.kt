package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.shop.models.OrderItem
import com.lionTF.CShop.domain.shop.models.OrderStatus

data class OrdersDTO(
    val member: Member,
    val orderStatus: OrderStatus,
    val orderAddress: String,
)
