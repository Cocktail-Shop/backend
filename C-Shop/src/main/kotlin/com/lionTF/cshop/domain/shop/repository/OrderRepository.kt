package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Orders, Long> {
}
