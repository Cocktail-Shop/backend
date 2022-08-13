package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.shop.models.CartItem
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CartItemRepository : JpaRepository<CartItem, Long> {
    @Query("select ci from CartItem ci where ci.item.itemId = :itemId")
    fun getCartItemByItemId(@Param("itemId")itemId: Long): CartItem
}