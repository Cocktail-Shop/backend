package com.lionTF.cshop.domain.admin.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.http.HttpStatus

data class ResponseItemDTO (
    val httpStatus: Int,
    val message: String,
    val result: ItemResultDTO? = null,
){
    companion object{
        fun itemToResponseItemPageDTO(itemResultDTO: ItemResultDTO): ResponseItemDTO {
            return ResponseItemDTO(
                HttpStatus.OK.value(),
                "상품 조회를 성공했습니다.",
                itemResultDTO
            )
        }
    }
}

data class ItemResultDTO (
    var itemName: String,
    var category: Category,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
)

data class RequestUpdateItemDTO(
    var itemName: String = "",
    var category: Category,
    var price: Int = 0,
    var amount: Int = 0,
    var degree: Int = 0,
    var itemDescription: String = "",
){
    companion object{
        fun formDTOFromResponseItemDTO(responseItemDTO: ResponseItemDTO):RequestUpdateItemDTO{
            return RequestUpdateItemDTO(
                itemName = responseItemDTO.result!!.itemName,
                category = responseItemDTO.result.category,
                price = responseItemDTO.result.price,
                amount = responseItemDTO.result.amount,
                degree = responseItemDTO.result.degree,
                itemDescription = responseItemDTO.result.itemDescription
            )
        }
    }
}
