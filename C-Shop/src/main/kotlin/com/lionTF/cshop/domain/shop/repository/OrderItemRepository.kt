package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.OrderItem
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItem, Long> {
}
