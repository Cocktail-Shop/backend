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
                " from WishList w where w.memberId = :memberId and w.wishListStatus = :wishListStatus",
        countQuery = "select count(w) from WishList w where w.memberId = :memberId and w.wishListStatus = :wishListStatus"
    )
    fun findWishListByMemberId(
        @Param("memberId") memberId: Long?,
        @Param("wishListStatus") wishListStatus: Boolean,
        pageable: Pageable
    ): Page<WishListDTO>
}
