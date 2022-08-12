package com.lionTF.CShop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class RequestOrderDTO(
    val memberId: Long?,
    var orderItems: List<RequestOrderItemDTO>,
    val orderAddress: String,
)

data class RequestOrderInfoDTO(
    var orderItems: List<RequestOrderItemDTO> = listOf(),
    val orderAddress: String = "",
)
data class RequestOrderItemDTO(
    val itemId: Long,
    val amount: Int,
    val price: Int,
)

data class RequestOrderResultDTO(
    val status: Int,
    val message: String,
    val errorItems: List<Long>?
)


fun setRequestOrderSuccessResultDTO() : RequestOrderResultDTO {
    return RequestOrderResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "상품 주문을 성공하였습니다.",
        errorItems = null,
    )
}

fun setRequestOrderFailResultDTO(errorItems: List<Long>) : RequestOrderResultDTO{
    return RequestOrderResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "상품 재고가 부족하여 주문하지 못하였습니다.",
        errorItems = errorItems,
    )
}