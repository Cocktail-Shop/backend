package com.lionTF.cshop.domain.shop.models

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Rollback

@Rollback
internal class OrdersUnitTest {

    private val order = Orders(
        orderId = 1L,
        orderStatus = OrderStatus.COMPLETE,
        orderAddress = "test",
        orderAddressDetail = "test",
        deliveryStatus = DeliveryStatus.READY
    )

    @Test
    @DisplayName("주문 취소 시 주문 상태와 배달 상태가 변하는지 test")
    fun cancelOrderTest() {
        //given

        //when
        order.cancelOrder()

        //then
        assertThat(order.orderStatus).isEqualTo(OrderStatus.CANCEL)
        assertThat(order.deliveryStatus).isEqualTo(DeliveryStatus.REFUND)
    }

    @Test
    @DisplayName("배달 상태가 배송 준비 -> 배송 중으로 변경되는 지 test")
    fun inDeliveryStatusTest() {
        //given

        //when
        order.inDeliveryStatus()

        //then
        assertThat(order.deliveryStatus).isEqualTo(DeliveryStatus.IN_DELIVERY)
    }

    @Test
    @DisplayName("배달 상태가 배송 중 -> 배송 완료로 변경되는 지 test")
    fun completeDeliveryTest() {
        //given

        //when
        order.completeDelivery()

        //then
        assertThat(order.deliveryStatus).isEqualTo(DeliveryStatus.COMPLETE)
    }
}
