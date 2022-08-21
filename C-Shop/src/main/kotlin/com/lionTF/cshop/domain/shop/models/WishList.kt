package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import javax.persistence.*

@Entity
class WishList(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val wishListId: Long = 0,

    val memberId: Long? = 0,
    val itemId: Long? = 0,

    @Enumerated(EnumType.STRING)
    val category: Category = Category.ALCOHOL,

    val itemName: String = "",
    val itemImgUrl: String = ""

    ) {

    companion object {
        fun toWishListEntity(item: Item, memberId: Long?): WishList {
            return WishList(
                memberId = memberId,
                itemId = item.itemId,
                category = item.category,
                itemName = item.itemName,
                itemImgUrl = item.itemImgUrl
            )
        }
    }
}