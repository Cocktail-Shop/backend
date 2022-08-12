package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.shop.models.OrderItem
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminOrderItemRepository: JpaRepository<OrderItem, Long> {

    @Query("select oi from OrderItem oi where oi.orders.orderId = :orderId")
    fun getOrderItemByOrdersId(@Param("orderId")orderId: Long): OrderItem
}