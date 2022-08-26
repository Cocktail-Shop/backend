package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.controller.dto.CocktailResultDTO
import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.repository.custom.AdminCocktailRepositoryCustom
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminCocktailRepository : JpaRepository<Cocktail, Long>, AdminCocktailRepositoryCustom {

    @Query(
        "select new com.lionTF.cshop.domain.admin.controller.dto.CocktailResultDTO(c.cocktailName, c.cocktailDescription, c.cocktailImgUrl)" +
                " from Cocktail c where c.cocktailId = :cocktailId"
    )
    fun findCocktailById(@Param("cocktailId") cocktailId: Long): CocktailResultDTO


    fun countAllByCocktailStatusIsTrue(): Long

    @Query("select c from Cocktail c where c.cocktailId = :cocktailId")
    fun findCocktail(@Param("cocktailId") cocktailId: Long): Cocktail?


    @Query("select c from Cocktail c where c.cocktailId = :cocktailId and c.cocktailStatus = :cocktailStatus")
    fun findCocktails(
        @Param("cocktailStatus") cocktailStatus: Boolean,
        @Param("cocktailId") cocktailId: Long
    ): Cocktail?
}
