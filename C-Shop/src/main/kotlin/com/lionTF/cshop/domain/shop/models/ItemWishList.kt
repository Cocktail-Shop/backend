package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class ItemWishList(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemWishListId: Long = 0,

    val memberId: Long? = 0,
    val itemId: Long? = 0,

    @Enumerated(EnumType.STRING)
    val category: Category = Category.ALCOHOL,

    val itemName: String = "",
    val itemImgUrl: String = "",
    val price: Int = 0,
) : BaseTimeEntity() {

    companion object {
        fun toEntity(item: Item, memberId: Long?): ItemWishList {
            return ItemWishList(
                memberId = memberId,
                itemId = item.itemId,
                category = item.category,
                itemName = item.itemName,
                itemImgUrl = item.itemImgUrl,
                price = item.price,
            )
        }
    }
}
