package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.shop.models.OrderStatus

data class OrdersDTO(
    val member: Member?,
    val orderStatus: OrderStatus,
    val Address: String,
    val AddressDetail: String,
)

