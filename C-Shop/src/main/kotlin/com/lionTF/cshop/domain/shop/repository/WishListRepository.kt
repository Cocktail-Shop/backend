package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.controller.dto.WishListDTO
import com.lionTF.cshop.domain.shop.models.WishList
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WishListRepository : JpaRepository<WishList, Long> {

    @Query("SELECT new com.lionTF.cshop.domain.shop.controller.dto.WishListDTO(w.wishListId, w.memberId, w.itemId, w.category, w.itemName, w.itemImgUrl)" +
            " from WishList w where w.memberId = :memberId")
    fun findWishListByMemberId(@Param("memberId")memberId: Long?): List<WishListDTO>
}