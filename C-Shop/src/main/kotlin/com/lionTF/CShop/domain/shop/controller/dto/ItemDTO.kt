package com.lionTF.CShop.domain.shop.controller.dto


import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.global.HttpStatus
import java.time.temporal.TemporalAmount


//상품 단건 조회시 상품 정보를 담기위한 dto
data class ReadItemDTO (
    val itemId: Long,
    val category: Category,
    var itemName: String,
    var price: Int,
    var totalAmount: Int,
    var degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,
    var itemStatus: Boolean,
)

//상품 단건 조회시 최종 응답 형태를 맞춰주기 위한 dto
data class ReadItemResultDTO(
    val status: HttpStatus,
    val message: String,
    val result: ReadItemDTO
)

//칵테일 단건 조회시 칵테일에 포함되어 있는 상품들의 정보를 담는 dto
data class ReadCocktailItemInfoDTO(
    val itemId: Long,
    val itemName: String,
    val price: Int,
    val amount: Int,
)