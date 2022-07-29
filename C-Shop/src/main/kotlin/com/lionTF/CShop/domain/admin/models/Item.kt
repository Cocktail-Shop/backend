package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.shop.controller.dto.CocktailItemInfoDTO
import com.lionTF.CShop.domain.shop.controller.dto.ItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ItemResultDTO
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.OrderItem
import com.lionTF.CShop.global.HttpStatus
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long,

    @OneToMany(mappedBy = "item")
    var cocktailItem: List<CocktailItem>,

    @OneToMany(mappedBy = "item")
    var orderItem: List<OrderItem>,

    @OneToMany(mappedBy = "item")
    var cartItem: List<CartItem>,

    var itemName: String,
    var price: Int,
    var amount: Int,
    var degree: Int,
    var itemDescription: String,
    var itemImgUrl: String,

    @Enumerated(EnumType.STRING) var category: Category,
    var itemStatus: Boolean,
)
