package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.controller.dto.ItemWishListDTO
import com.lionTF.cshop.domain.shop.models.ItemWishList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ItemWishListRepository : JpaRepository<ItemWishList, Long> {

    @Query(
        value = "SELECT new com.lionTF.cshop.domain.shop.controller.dto.ItemWishListDTO(w.itemWishListId, w.memberId, w.itemId, w.category, w.itemName, w.itemImgUrl, w.price)" +
                " from ItemWishList w where w.memberId = :memberId",
        countQuery = "select count(w) from ItemWishList w where w.memberId = :memberId"
    )
    fun findWishListByMemberId(
        @Param("memberId") memberId: Long,
        pageable: Pageable
    ): Page<ItemWishListDTO>


    fun deleteWishListByItemId(itemId: Long)

    @Query("select w from ItemWishList w where w.memberId = :memberId and w.itemId = :itemId")
    fun findItemWishList(
        @Param("memberId") memberId: Long,
        @Param("itemId") itemId: Long
    ): ItemWishList?
}
