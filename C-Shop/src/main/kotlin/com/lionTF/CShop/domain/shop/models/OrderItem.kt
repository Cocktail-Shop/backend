package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.shop.controller.dto.OrderItemDTO
import com.lionTF.CShop.global.model.BaseTimeEntity
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
    val orderItemId: Long=0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    var orders: Orders,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    var amount: Int = 0,
) : BaseTimeEntity() {


    // 주문 취소
    fun cancel() {
        item.addAmount(amount)
    }
}

    fun orderItemDTOtoOrderItem(orderItemDTO: OrderItemDTO) : OrderItem {
    return OrderItem(
        orders = orderItemDTO.orders,
        item = orderItemDTO.item,
        amount = orderItemDTO.amount,
    )
}