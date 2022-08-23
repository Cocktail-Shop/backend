package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.web.multipart.MultipartFile

data class ItemCreateRequestDTO(
    val itemName: String = "",
    val category: Category = Category.ALCOHOL,
    val price: Int = 0,
    val amount: Int = 0,
    val degree: Int = 0,
    val itemDescription: String = "",
    val itemImgUrl: MultipartFile? = null,
) {

    companion object {
        fun toFormDTO(): ItemCreateRequestDTO {
            return ItemCreateRequestDTO()
        }
    }
}
