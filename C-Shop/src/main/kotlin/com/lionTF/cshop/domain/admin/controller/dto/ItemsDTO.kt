package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ItemsSearchDTO(
    val httpStatus: Int,
    val message: String,
    val itemName: String? = null,
    val result: Page<ItemsDTO>? = null,
) {

    companion object {
        fun toFormDTO(
            findItemDTO: Page<ItemsDTO>,
            itemName: String? = ""
        ): ItemsSearchDTO {

            return ItemsSearchDTO(
                HttpStatus.OK.value(),
                "상품 조회를 성공했습니다.",
                itemName,
                findItemDTO
            )
        }
    }
}

data class ItemsDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
    val degree: Int,
    val itemDescription: String,
    val itemImgUrl: String,
    val category: Category,
    val createdAt: LocalDateTime,
)
