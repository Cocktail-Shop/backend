package com.lionTF.cshop.domain.shop.controller.dto

import org.springframework.http.HttpStatus

data class OrderResponseDTO(val httpStatus: Int, val message: String, var href: String = "" ) {
    companion object {
        fun toSuccessDeleteItemResponseDTO(): OrderResponseDTO {
            return OrderResponseDTO(HttpStatus.NO_CONTENT.value(), "상품이 정삭적으로 삭제되었습니다.", "/items")
        }

        fun toFailDeleteItemResponseDTO(): OrderResponseDTO {
            return OrderResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "존재하지 않는 상품입니다.", "/items")
        }

        fun toSuccessSearchShopOrdersDTO(): OrderResponseDTO {
            return OrderResponseDTO(HttpStatus.OK.value(), "주문 조회를 성공했습니다.", "/orders")
        }

        fun toFailSearchShopOrdersDTO(): OrderResponseDTO {
            return OrderResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "주문 조회를 실패했습니다.", "/items")
        }
    }
}