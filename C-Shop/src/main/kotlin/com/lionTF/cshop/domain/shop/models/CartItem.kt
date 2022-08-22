package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.shop.controller.dto.CartItemDTO
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class CartItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartItemId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    var cart: Cart,

    var amount: Int,
) : BaseTimeEntity() {

    companion object {
        fun fromCartItemDTO(cartItemDTO: CartItemDTO): CartItem {
            return CartItem(
                item = cartItemDTO.item,
                cart = cartItemDTO.cart,
                amount = cartItemDTO.amount,
            )
        }
    }
}
