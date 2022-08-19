package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.shop.models.CartItem
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface CartItemRepository : JpaRepository<CartItem, Long> {
    @Modifying
    @Transactional
    @Query("delete from CartItem ci where ci.cartItemId = :cartItemId and ci.cart.cartId = :cartId and ci.item.itemId = :itemId")
    fun deleteCartItem(@Param("cartItemId") cartItemId: Long, @Param("cartId") cartId: Long, @Param("itemId") itemId: Long)
}