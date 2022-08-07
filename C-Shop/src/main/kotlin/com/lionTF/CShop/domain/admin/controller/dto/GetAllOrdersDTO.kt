package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.shop.models.OrderStatus
import org.springframework.data.domain.Page

data class GetAllOrdersDTO(
    var orderId: Long,
    var orderStatus: OrderStatus,
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var amount: Int,
    var itemImgUrl: String,
    var memberId: Long,
    var id: String,
    var memberName: String,
)

data class GetAllOrdersResultDTO(
    var httpStatus: Int,
    var message: String,
    var orders: Page<GetAllOrdersDTO>
)
