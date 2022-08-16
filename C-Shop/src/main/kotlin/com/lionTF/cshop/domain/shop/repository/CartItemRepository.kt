package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.CartItem
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CartItemRepository : JpaRepository<CartItem, Long> {
    @Query("select ci from CartItem ci where ci.cartItemId = :cartItemId")
    fun getCartItemByCartItemId(@Param("cartItemId")cartItemId: Long): CartItem
}