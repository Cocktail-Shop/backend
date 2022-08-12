package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.shop.models.Cart
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CartRepository : JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.member.memberId = :memberId")
    fun getCart(@Param("memberId") memberId: Long?) : Cart


}