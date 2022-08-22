package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.shop.models.DeliveryStatus
import com.lionTF.cshop.domain.shop.models.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class OrdersSearchDTO(
    val httpStatus: Int,
    val message: String,
    val keyword: String? = null,
    val result: Page<OrdersDTO>? = null
) {
    companion object {
        fun orderToResponseOrderSearchPageDTO(
            findOrdersDTO: Page<OrdersDTO>,
            keyword: String? = ""
        ): OrdersSearchDTO {
            return OrdersSearchDTO(
                HttpStatus.OK.value(),
                "주문 조회를 성공했습니다.",
                keyword,
                findOrdersDTO
            )
        }
    }
}

data class OrdersDTO(
    val orderId: Long,
    val orderStatus: OrderStatus,
    val deliveryStatus: DeliveryStatus,
    val orderAddress: String,
    val orderAddressDetail: String,
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
    val createdAt: LocalDateTime,
    val itemImgUrl: String,
    val memberId: Long,
    val id: String,
    val memberName: String,
)
