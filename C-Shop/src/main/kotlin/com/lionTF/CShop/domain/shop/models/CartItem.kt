package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.admin.models.Item
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
    private val cartItemId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private var cart: Cart,

    private var amount: Int,

    ) {

}