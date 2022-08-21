package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.admin.controller.dto.ItemCreateRequestDTO
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class WishList(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val wishListId: Long = 0,

    val memberId: Long? = 0,
    val itemId: Long? = 0,
    val category: Category = Category.ALCOHOL,
    val itemName: String = "",

    ) {


    companion object {
        fun toWishListEntity(item: Item, memberId: Long?): WishList {
            return WishList(
                memberId = memberId,
                itemId = item.itemId,
                category = item.category,
                itemName = item.itemName
            )
        }
    }
}