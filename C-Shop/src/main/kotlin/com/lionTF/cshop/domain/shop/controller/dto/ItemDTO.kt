package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.http.HttpStatus

data class ItemDTO(
    val itemId: Long,
    val category: Category,
    var itemName: String,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,
    var itemStatus: Boolean,
) {

    companion object {
        fun fromItem(item: Item): ItemDTO {
            return ItemDTO(
                itemId = item.itemId,
                category = item.category,
                itemName = item.itemName,
                price = item.price,
                amount = item.amount,
                degree = item.degree,
                itemDescription = item.itemDescription,
                itemImgUrl = item.itemImgUrl,
                itemStatus = item.itemStatus,
            )
        }
    }
}

data class ItemResultDTO(
    val status: Int,
    val message: String,
    val result: ItemDTO
) {
    companion object {
        fun setItemResultDTO(result: ItemDTO): ItemResultDTO {
            return ItemResultDTO(
                status = HttpStatus.OK.value(),
                message = "상품 조회 성공",
                result = result
            )
        }
    }
}
