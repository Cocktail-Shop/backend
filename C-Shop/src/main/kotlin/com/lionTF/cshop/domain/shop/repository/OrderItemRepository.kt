package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderItemRepository : JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi where oi.orders.orderId = :orderId")
    fun getOrderItemByOrdersId(@Param("orderId") orderId: Long): List<OrderItem>
}
