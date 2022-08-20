package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.http.HttpStatus

data class ItemResponseDTO(
    val httpStatus: Int,
    val message: String,
    val result: ItemResultDTO? = null,
) {
    companion object {
        fun itemToResponseItemPageDTO(itemResultDTO: ItemResultDTO): ItemResponseDTO {
            return ItemResponseDTO(
                HttpStatus.OK.value(),
                "상품 조회를 성공했습니다.",
                itemResultDTO
            )
        }
    }
}

data class ItemResultDTO(
    val itemName: String,
    val category: Category,
    val price: Int,
    val amount: Int,
    val degree: Int,
    val itemDescription: String,
    val itemImgUrl: String,
)

data class ItemUpdateRequestDTO(
    val itemName: String = "",
    val category: Category,
    val price: Int = 0,
    val amount: Int = 0,
    val degree: Int = 0,
    val itemDescription: String = "",
    val itemImgUrl: String = "",
) {
    companion object {
        fun formDTOFromResponseItemDTO(responseItemDTO: ItemResponseDTO): ItemUpdateRequestDTO? {
            return responseItemDTO.result?.let {
                ItemUpdateRequestDTO(
                    itemName = it.itemName,
                    category = responseItemDTO.result.category,
                    price = responseItemDTO.result.price,
                    amount = responseItemDTO.result.amount,
                    degree = responseItemDTO.result.degree,
                    itemDescription = responseItemDTO.result.itemDescription,
                    itemImgUrl = responseItemDTO.result.itemImgUrl
                )
            }
        }
    }
}
