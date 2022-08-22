package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.Cart
import com.lionTF.cshop.domain.shop.repository.custom.CartRepositoryCustom
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CartRepository : JpaRepository<Cart, Long>, CartRepositoryCustom {

    @Query("select c from Cart c where c.member.memberId = :memberId")
    fun getCart(@Param("memberId") memberId: Long?): Cart

    @Query("select c.cartId from Cart c where c.member.memberId = :memberId")
    fun getCartId(@Param("memberId") memberId: Long): Long
}
