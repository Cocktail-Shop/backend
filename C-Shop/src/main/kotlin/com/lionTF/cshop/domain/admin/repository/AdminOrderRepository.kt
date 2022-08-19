package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.repository.custom.AdminOrderRepositoryCustom
import com.lionTF.cshop.domain.shop.models.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AdminOrderRepository : JpaRepository<Orders, Long>, AdminOrderRepositoryCustom {

    @Query("select o from Orders o where o.orderId = :orderId")
    fun findOrders(@Param("orderId") orderId: Long): Orders?
}
