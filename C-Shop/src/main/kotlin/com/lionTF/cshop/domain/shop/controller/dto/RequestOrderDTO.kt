package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import org.springframework.http.HttpStatus

data class RequestOrderDTO(
    val memberId: Long?,
    var orderItems: List<RequestOrderItemDTO>,
    var address: String,
    var addressDetail: String,
)

data class RequestOrderInfoDTO(
    var orderItems: MutableList<RequestOrderItemDTO> = mutableListOf(RequestOrderItemDTO(0,0,0)),
    var address: String = "",
    var addressDetail: String="",
){
    companion object{
        fun toFormRequestItemOrderInfoDTO(item:ItemDTO,addressDTO: AddressDTO):RequestOrderInfoDTO{
            return RequestOrderInfoDTO(
                orderItems = mutableListOf( RequestOrderItemDTO(item.itemId,0,item.price)),
                address=addressDTO.Address,
                addressDetail = addressDTO.AddressDetail
            )
        }
    }
}
data class RequestOrderItemDTO(
    val itemId: Long,
    val amount: Int,
    val price: Int,
)

data class RequestOrderResultDTO(
    val status: Int,
    val message: String,
    val href: String,
){
    companion object{
        fun setRequestOrderSuccessResultDTO() : RequestOrderResultDTO {
            return RequestOrderResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "상품 주문을 성공하였습니다.",
                href = "/items"
            )
        }

        fun setRequestOrderStatusFailResultDTO() : RequestOrderResultDTO{
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이 존재하여 주문하지 못했습니다.",
                href = "items"
            )
        }

        fun setRequestOrderAmountFailResultDTO() : RequestOrderResultDTO{
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "상품 재고가 부족하여 주문하지 못하였습니다.",
                href = "items"
            )
        }
        fun setNotPositiveError() : RequestOrderResultDTO{
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "요청 상품 수량은 양수여야 합니다.",
                href = "items"
            )
        }
    }
}



