package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.admin.controller.dto.itemToItem
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.shop.controller.dto.CartItemDTO
import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    }

fun cartItemDTOToCartItem(cartItemDTO: CartItemDTO) : CartItem {
    return CartItem(
        item = cartItemDTO.item,
        cart = cartItemDTO.cart,
        amount = cartItemDTO.amount,
    )
}

