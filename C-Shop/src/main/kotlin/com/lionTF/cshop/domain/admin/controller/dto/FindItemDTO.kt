package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ResponseSearchItemSearchDTO(
    val httpStatus: Int,
    val message: String,
    val keyword: String? = null,
    val result: Page<FindItemDTO>? = null,
){
    companion object{
        fun itemToResponseItemSearchPageDTO(findItemDTO: Page<FindItemDTO>, keyword: String?): ResponseSearchItemSearchDTO {
            return ResponseSearchItemSearchDTO(
                HttpStatus.OK.value(),
                "상품 조회를 성공했습니다.",
                keyword,
                findItemDTO
            )
        }
    }
}

data class FindItemDTO(
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,
    var category: Category,
    var createdAt: LocalDateTime,
)
