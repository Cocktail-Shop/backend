package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.web.multipart.MultipartFile

// 상품 등록을 위한 정보가 실려오는 JSON 형태를 DB에 저장하기 위한 dto
data class RequestCreateItemDTO(
    val itemName: String = "",
    val category: Category= Category.ALCOHOL,
    val price: Int = 0,
    val amount: Int = 0,
    val degree: Int = 0,
    val itemDescription: String = "",
    val itemImgUrl: MultipartFile? = null,

) {
    companion object {
        fun toFormDTO(): RequestCreateItemDTO {
            return RequestCreateItemDTO()
        }
    }
}