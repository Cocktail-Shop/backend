package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.global.HttpStatus


//상품 단건 조회시 상품 정보를 담기위한 dto
data class ItemDTO (
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
data class ItemResultDTO(
    val status: Int,
    val message: String,
    val result: ItemDTO
)

//아이템에 관한 정보를 dto로 변환하는 메소드
fun ItemToItemDTO(item: Item): ItemDTO {
    return ItemDTO(
        itemId = item.itemId,
        category = item.category,
        itemName = item.itemName,
        price = item.price,
        totalAmount = item.amount,
        degree = item.degree,
        itemDescription = item.itemDescription,
        itemImgUrl = item.itemImgUrl,
        itemStatus = item.itemStatus,
    )
}

//상품 단건 조회시, 응답 형태를 맞춰주기 위한 메소드
fun setItemResultDTO(result: ItemDTO): ItemResultDTO {
    return ItemResultDTO(
        status = HttpStatus.OK.code,
        message = "상품 조회 성공",
        result = result
    )
}

