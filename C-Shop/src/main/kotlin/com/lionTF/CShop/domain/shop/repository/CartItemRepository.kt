package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.shop.models.CartItem
import org.springframework.data.jpa.repository.JpaRepository

interface CartItemRepository : JpaRepository<CartItem, Long> {

}