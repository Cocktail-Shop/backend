package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.CartItem
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface CartItemRepository : JpaRepository<CartItem, Long> {

    @Modifying
    @Transactional
    @Query("delete from CartItem ci where ci.cartItemId = :cartItemId")
    fun deleteCartItem(@Param("cartItemId") cartItemId: Long)
}
