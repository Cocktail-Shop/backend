package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.OrderItem
import lombok.*
import javax.persistence.*

@Entity
@Builder
class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long = 0,

    @OneToMany(mappedBy = "item")
    var cocktailItem: List<CocktailItem>? = null,

    @OneToMany(mappedBy = "item")
    var cartItem: List<CartItem>? = null,

    @OneToMany(mappedBy = "item")
    var orderItem: List<OrderItem>? = null,

    var itemName: String,

    var price: Int = 0,
    var amount: Int = 0,
    var degree: Int = 0,
    var itemDescription: String ="",
    var itemImgUrl: String = "",

    @Enumerated(EnumType.STRING)
    var category: Category,

    var itemStatus: Boolean,
) {

    // 상품 수정
    fun update(createItemDTO: CreateItemDTO) {
        itemName = createItemDTO.itemName
        category = createItemDTO.category
        price = createItemDTO.price
        amount = createItemDTO.amount
        degree = createItemDTO.degree
        itemDescription = createItemDTO.itemDescription
    }

    fun delete() {
        itemStatus = false
    }
}
