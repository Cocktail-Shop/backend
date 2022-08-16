package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.models.CocktailItem
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminCocktailItemRepository : JpaRepository<CocktailItem, Long> {

    @Query("select ci from CocktailItem ci where ci.cocktail.cocktailId = :cocktailId")
    fun findAllByCocktailId(@Param("cocktailId") cocktailId: Long): MutableList<CocktailItem>

    @Query("select ci.item.itemId from CocktailItem ci where ci.cocktail.cocktailId = :cocktailId")
    fun findItemIdByCocktailId(@Param("cocktailId")cocktailId: Long): MutableList<Long>

    @Query("select count(ci) from CocktailItem ci where ci.cocktail.cocktailId = :cocktailId")
    fun countAllByCocktailId(@Param("cocktailId") cocktailId: Long): Long
}