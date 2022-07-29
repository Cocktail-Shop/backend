package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.global.HttpStatus

// 상품 등록을 위한 정보가 실려오는 JSON 형태를 DB에 저장하기 위한 dto
data class createItemDTO(
    var itemName: String,
    var category: Category,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
)

data class createItemResultDTO(
    val status: Int,
    val message: String,
)

fun itemToItem(createItemDTO: createItemDTO): Item {

    return Item(
        itemName = createItemDTO.itemName,
        category = createItemDTO.category,
        price = createItemDTO.price,
        amount = createItemDTO.amount,
        degree = createItemDTO.degree,
        itemDescription = createItemDTO.itemDescription,
        itemStatus = true,
    )
}

fun setCreateSuccessItemResultDTO(): createItemResultDTO {
    return createItemResultDTO(
        status = HttpStatus.Created.code,
        message = "상품이 등록되었습니다."
    )
}

fun setCreateFailItemResultDTO(): createItemResultDTO {
    return createItemResultDTO(
        status = HttpStatus.InternalServerError.code,
        message = "이미 존재하는 상품입니다."
    )
}