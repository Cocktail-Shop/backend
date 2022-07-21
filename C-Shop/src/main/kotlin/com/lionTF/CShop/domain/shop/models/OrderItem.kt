package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.admin.models.Item
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class OrderItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val orderItemId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    private var orders: Orders,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private var item: Item,

    private var amount: Int,
) {

}