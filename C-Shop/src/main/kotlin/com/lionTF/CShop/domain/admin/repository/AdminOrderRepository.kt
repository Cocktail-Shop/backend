package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.shop.models.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface AdminOrderRepository: JpaRepository<Orders, Long> {
}