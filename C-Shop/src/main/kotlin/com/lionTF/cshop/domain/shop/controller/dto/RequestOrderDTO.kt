package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import org.springframework.http.HttpStatus

data class RequestOrderDTO(
    val memberId: Long,
    var orderItems: List<RequestOrderItemDTO>,
    var address: String,
    var addressDetail: String,
) {


    fun setAddress(addressDTO: AddressDTO) {
        address = addressDTO.Address
        addressDetail = addressDTO.AddressDetail
    }

    companion object {
        fun toRequestOrderDTO(memberId: Long, requestOrderInfoDTO: RequestOrderInfoDTO): RequestOrderDTO {
            return RequestOrderDTO(
                memberId = memberId,
                orderItems = requestOrderInfoDTO.orderItems,
                address = requestOrderInfoDTO.address,
                addressDetail = requestOrderInfoDTO.addressDetail
            )
        }
    }
}

data class RequestOrderInfoDTO(
    var orderItems: MutableList<RequestOrderItemDTO> = mutableListOf(RequestOrderItemDTO(0, 0, 0, "")),
    var address: String = "",
    var addressDetail: String = "",
) {

    companion object {
        fun toFormRequestItemOrderInfoDTO(item: ItemDTO, addressDTO: AddressDTO): RequestOrderInfoDTO {
            return RequestOrderInfoDTO(
                orderItems = mutableListOf(RequestOrderItemDTO(item.itemId, 0, item.price, item.itemName)),
                address = addressDTO.Address,
                addressDetail = addressDTO.AddressDetail
            )
        }

        fun toFormRequestCocktailOrderInfoDTO(
            cocktailDTO: MutableList<RequestOrderItemDTO>,
            addressDTO: AddressDTO
        ): RequestOrderInfoDTO {
            return RequestOrderInfoDTO(
                orderItems = cocktailDTO,
                address = addressDTO.Address,
                addressDetail = addressDTO.AddressDetail
            )
        }
    }
}

data class RequestOrderItemDTO(
    var itemId: Long = 0,
    var amount: Int = 0,
    var price: Int = 0,
    var itemName: String = "",
) {

    companion object {
        fun fromCocktailItemDTO(cocktailItemDTO: CocktailItemDTO): RequestOrderItemDTO {
            return RequestOrderItemDTO(
                itemId = cocktailItemDTO.itemId,
                amount = 0,
                price = cocktailItemDTO.price,
                itemName = cocktailItemDTO.itemName
            )
        }

        fun fromCartItemDTO(findCartDTO: FindCartDTO): RequestOrderItemDTO {
            return RequestOrderItemDTO(
                itemId = findCartDTO.itemId,
                amount = findCartDTO.amount,
                price = findCartDTO.price,
                itemName = findCartDTO.itemName
            )
        }
    }
}

data class RequestOrderResultDTO(
    val status: Int,
    val message: String,
    val href: String,
) {

    companion object {
        fun setRequestOrderSuccessResultDTO(): RequestOrderResultDTO {
            return RequestOrderResultDTO(
                status = HttpStatus.CREATED.value(),
                message = "상품 주문을 성공하였습니다.",
                href = "/items"
            )
        }

        fun setRequestOrderStatusFailResultDTO(): RequestOrderResultDTO {
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "삭제된 상품이 존재하여 주문하지 못했습니다.",
                href = "items"
            )
        }

        fun setRequestOrderAmountFailResultDTO(): RequestOrderResultDTO {
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "상품 재고가 부족하여 주문하지 못하였습니다.",
                href = "items"
            )
        }

        fun setNotPositiveError(): RequestOrderResultDTO {
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "요청 상품 수량은 양수여야 합니다.",
                href = "items"
            )
        }

        fun setRequestOrderAllZeroFailResultDTO(): RequestOrderResultDTO {
            return RequestOrderResultDTO(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                message = "적어도 한 개 이상의 상품은 주문하셔야 합니다.",
                href = "items"
            )
        }
    }
}
