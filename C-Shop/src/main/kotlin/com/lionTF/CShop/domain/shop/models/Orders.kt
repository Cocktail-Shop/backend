package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.shop.controller.dto.OrdersDTO
import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Orders(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long = 0,

    @OneToMany(mappedBy = "orders")
    var orderItem: List<OrderItem>?=null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,

    @Enumerated(EnumType.STRING)
    var orderStatus: OrderStatus = OrderStatus.COMPLETE,

    @Enumerated(EnumType.STRING)
    var deliveryStatus: DeliveryStatus = DeliveryStatus.READY,

    var orderAddress: String = "",
    var orderAddressDetail: String = "",
) : BaseTimeEntity(){

    // 주문 삭제
    fun cancelOrder(){
        orderStatus = OrderStatus.CANCEL
    }

    companion object{
        fun ordersDTOToOrders(ordersDTO: OrdersDTO) : Orders {
            return Orders(
                //orderItem = ordersDTO.orderItem,
                member = ordersDTO.member,
                orderStatus = ordersDTO.orderStatus,
                orderAddress = ordersDTO.Address,
                orderAddressDetail = ordersDTO.AddressDetail
            )
        }
    }
}


