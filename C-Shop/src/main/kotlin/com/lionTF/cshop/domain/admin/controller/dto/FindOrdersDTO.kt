package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.shop.models.DeliveryStatus
import com.lionTF.cshop.domain.shop.models.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime


data class ResponseSearchOrdersResultDTO(
    var httpStatus: Int,
    var message: String,
    val keyword: String? = null,
    var result: Page<FindOrdersDTO>? = null
){
    companion object{
        fun orderToResponseOrderSearchPageDTO(findOrdersDTO: Page<FindOrdersDTO>, keyword: String?): ResponseSearchOrdersResultDTO {
            return ResponseSearchOrdersResultDTO(
                HttpStatus.OK.value(),
                "주문 조회를 성공했습니다.",
                keyword,
                findOrdersDTO
            )
        }
    }

}

data class FindOrdersDTO(
    var orderId: Long,
    var orderStatus: OrderStatus,
    var deliveryStatus: DeliveryStatus,
    var orderAddress: String,
    var orderAddressDetail: String,
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var amount: Int,
    var createdAt: LocalDateTime,
    var itemImgUrl: String,
    var memberId: Long,
    var id: String,
    var memberName: String,
)