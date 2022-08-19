package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.shop.models.OrderItem
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminOrderItemRepository: JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi where oi.orders.orderId = :orderId")
    fun getOrderItemByOrdersId(@Param("orderId")orderId: Long): OrderItem
}
