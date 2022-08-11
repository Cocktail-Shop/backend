package com.lionTF.CShop.domain.admin.controller.dto

import org.springframework.http.HttpStatus

data class AdminResponseDTO(val httpStatus: Int, val message: String, var href: String = "") {

    companion object {
        fun toSuccessCreateItemResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.CREATED.value(), "아이템이 정상적으로 생성되었습니다.", "/admins/all-item")
        }

        fun toFailCreateItemResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미 존재하는 상품입니다.", "/admins/all-item")
        }

        fun toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "가격과 수량은 0이하가 될 수 없습니다.", "/admins/all-item")
        }

        fun toFailCreateItemByInvalidFormatPriceResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "가격은 음수 또는 0원이 될 수 없습니다.", "/admins/all-item")
        }

        fun toFailCreateItemByInvalidFormatAmountResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "수량은 음수 또는 0이 될 수 없습니다.", "/admins/all-item")
        }

        fun toSuccessUpdateItemResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.CREATED.value(), "상품이 정상적으로 수정되었습니다.", "/admins/all-item")
        }

        fun toFailUpdateItemResponseDTO(): AdminResponseDTO {
            return AdminResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "존재하지 않는 상품입니다.", "/admins/all-item")
        }
    }
}
