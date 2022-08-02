package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.shop.models.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Long>{

}