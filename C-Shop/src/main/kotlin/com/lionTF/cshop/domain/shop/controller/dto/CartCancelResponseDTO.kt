package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import org.springframework.http.HttpStatus

class CartCancelResponseDTO (
    val httpStatus: Int,
    val message: String,
    var href: String = ""
){
    companion object {

        fun toFailCancelOrderResponseDTO(): CartCancelResponseDTO {
            return CartCancelResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "존재하지 않는 주문입니다.", "/orders")
        }

        fun toFailCancelOrderByDuplicatedResponseDTO(): CartCancelResponseDTO {
            return CartCancelResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미 취소된 주문은 취소할 수 없습니다.", "/orders")
        }

        fun toFailCancelOrderByCompleteDeliveryResponseDTO(): CartCancelResponseDTO {
            return CartCancelResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "배송이 완료된 주문은 취소할 수 없습니다.", "/orders")
        }

        fun toSuccessCancelOrderResponseDTO(): CartCancelResponseDTO {
            return CartCancelResponseDTO(HttpStatus.NO_CONTENT.value(), "주문이 정상적으로 취소되었습니다.", "/orders")
        }
    }
}