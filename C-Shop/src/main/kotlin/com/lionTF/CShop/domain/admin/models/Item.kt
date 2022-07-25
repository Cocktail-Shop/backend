package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.OrderItem
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
    private val itemId: Long,

    @OneToMany(mappedBy = "item")
    private var cocktailItem: List<CocktailItem>,

    @OneToMany(mappedBy = "item")
    private var orderItem: List<OrderItem>,

    @OneToMany(mappedBy = "item")
    private var cartItem: List<CartItem>,

    private var price: Int,
    private var amount: Int,
    private var degree: Int,
    private var itemDescription: String,
    private var itemImgUrl: String,

    @Enumerated(EnumType.STRING)
    private var category: Category,
    private var itemStatus: Boolean,
) {

}
