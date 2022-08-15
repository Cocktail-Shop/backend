package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.shop.models.OrderStatus
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ResponseSearchShopOrdersResultDTO(
    var httpStatus: Int,
    var message: String,
    val keyword: String? = null,
    var result: Page<FindShopOrdersDTO>? = null
){
    companion object{
        fun orderToResponseShopOrderSearchPageDTO(findShopOrdersDTO: Page<FindShopOrdersDTO>, keyword: String?): ResponseSearchShopOrdersResultDTO {
            return ResponseSearchShopOrdersResultDTO(
                HttpStatus.OK.value(),
                "상품 조회를 성공했습니다.",
                keyword,
                findShopOrdersDTO
            )
        }
    }

}

data class FindShopOrdersDTO(
    var memberId: Long,
    var orderId: Long,
    var createdAt: LocalDateTime,
    var orderStatus: OrderStatus,
    var orderAddress: String,
    var orderAddressDetail: String,
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var amount: Int,
)