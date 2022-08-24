package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.controller.dto.WishListDTO
import com.lionTF.cshop.domain.shop.models.WishList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface WishListRepository : JpaRepository<WishList, Long> {

    @Query(
        value = "SELECT new com.lionTF.cshop.domain.shop.controller.dto.WishListDTO(w.wishListId, w.memberId, w.itemId, w.category, w.itemName, w.itemImgUrl, w.price)" +
                " from WishList w where w.memberId = :memberId",
        countQuery = "select count(w) from WishList w where w.memberId = :memberId"
    )
    fun findWishListByMemberId(
        @Param("memberId") memberId: Long?,
        pageable: Pageable
    ): Page<WishListDTO>


    fun deleteWishListByItemId(itemId: Long)

    @Query("select w from WishList w where w.memberId = :memberId and w.itemId = :itemId")
    fun findWishList(
        @Param("memberId") memberId: Long,
        @Param("itemId") itemId: Long
    ): WishList?
}
