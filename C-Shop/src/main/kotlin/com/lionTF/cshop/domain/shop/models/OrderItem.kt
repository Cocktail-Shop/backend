package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.shop.controller.dto.OrderItemDTO
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
class OrderItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderItemId: Long=0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id")
    var orders: Orders? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item? = null,

    var amount: Int,
) : BaseTimeEntity(){

    // 주문 취소
    fun cancel() {
        item?.addAmount(amount)
    }
    companion object{
        fun fromOrderItemDTO(orderItemDTO: OrderItemDTO) : OrderItem {
            return OrderItem(
                orders = orderItemDTO.orders,
                item = orderItemDTO.item,
                amount = orderItemDTO.amount,
            )
        }
    }
}

