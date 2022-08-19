package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.shop.controller.dto.OrdersDTO
import com.lionTF.cshop.global.model.BaseTimeEntity
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
        deliveryStatus = DeliveryStatus.REFUND
    }

    fun updateDeliveryStatusInDelivery() {
        deliveryStatus = DeliveryStatus.IN_DELIVERY
    }

    fun updateDeliveryStatusComplete() {
        deliveryStatus = DeliveryStatus.COMPLETE
    }

    companion object{
        fun fromOrdersDTO(ordersDTO: OrdersDTO) : Orders {
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



