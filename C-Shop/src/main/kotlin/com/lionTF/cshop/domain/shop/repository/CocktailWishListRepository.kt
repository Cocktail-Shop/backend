package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.controller.dto.CocktailWishListDTO
import com.lionTF.cshop.domain.shop.controller.dto.ItemWishListDTO
import com.lionTF.cshop.domain.shop.models.CocktailWishList
import com.lionTF.cshop.domain.shop.models.ItemWishList
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CocktailWishListRepository : JpaRepository<CocktailWishList, Long> {

    @Query(
        value = "SELECT new com.lionTF.cshop.domain.shop.controller.dto.CocktailWishListDTO(c.cocktailWishListId, c.memberId, c.cocktailId, c.cocktailName, c.cocktailImgUrl)" +
                " from CocktailWishList c where c.memberId = :memberId",
        countQuery = "select count(c) from CocktailWishList c where c.memberId = :memberId"
    )
    fun findWishListByMemberId(
        @Param("memberId") memberId: Long,
        pageable: Pageable
    ): Page<CocktailWishListDTO>


    fun deleteWishListByCocktailId(itemId: Long)

    @Query("select c from CocktailWishList c where c.memberId = :memberId and c.cocktailId = :cocktailId")
    fun findCocktailWishList(
        @Param("memberId") memberId: Long,
        @Param("cocktailId") cocktailId: Long
    ): CocktailWishList?
}
