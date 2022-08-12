package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.admin.models.Category

// 상품 등록을 위한 정보가 실려오는 JSON 형태를 DB에 저장하기 위한 dto
data class RequestCreateItemDTO(
    var itemName: String = "",
    var category: Category= Category.ALCOHOL,
    var price: Int = 0,
    var amount: Int = 0,
    var degree: Int = 0,
    var itemDescription: String = "",

) {
    companion object {
        fun toFormDTO(): RequestCreateItemDTO {
            return RequestCreateItemDTO()
        }
    }
}