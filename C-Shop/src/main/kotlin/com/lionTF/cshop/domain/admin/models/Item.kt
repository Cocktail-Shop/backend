package com.lionTF.cshop.domain.admin.models

import com.lionTF.cshop.domain.admin.controller.dto.ItemCreateRequestDTO
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long = 0,

    var itemName: String = "",

    var price: Int = 0,
    var amount: Int = 0,
    var degree: Int = 0,
    var itemDescription: String = "",
    var itemImgUrl: String = "",

    @Enumerated(EnumType.STRING)
    var category: Category,

    var itemStatus: Boolean,
) : BaseTimeEntity() {

    fun update(requestCreateItemDTO: ItemCreateRequestDTO, itemImgUrl: String?) {
        itemName = requestCreateItemDTO.itemName
        category = requestCreateItemDTO.category
        price = requestCreateItemDTO.price
        amount = requestCreateItemDTO.amount
        degree = requestCreateItemDTO.degree
        itemDescription = requestCreateItemDTO.itemDescription
        this.itemImgUrl = itemImgUrl!!
    }

    fun delete() {
        itemStatus = false
    }


    fun addAmount(quantity: Int) {
        amount += quantity
    }

    companion object {
        fun fromItemCreateRequestDTO(
            requestCreateItemDTO: ItemCreateRequestDTO,
            itemImgUrl: String?
        ): Item {
            return Item(
                itemName = requestCreateItemDTO.itemName,
                category = requestCreateItemDTO.category,
                price = requestCreateItemDTO.price,
                amount = requestCreateItemDTO.amount,
                degree = requestCreateItemDTO.degree,
                itemDescription = requestCreateItemDTO.itemDescription,
                itemStatus = true,
                itemImgUrl = itemImgUrl!!
            )
        }
    }
}
