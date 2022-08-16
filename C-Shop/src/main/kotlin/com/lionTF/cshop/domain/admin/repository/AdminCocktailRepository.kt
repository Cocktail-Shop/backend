package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.controller.dto.CocktailResultDTO
import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.repository.custom.AdminCocktailRepositoryCustom
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminCocktailRepository : JpaRepository<Cocktail, Long>, AdminCocktailRepositoryCustom {

    @Query("select ct.cocktailName from Cocktail ct where ct.cocktailName = :cocktailName and ct.cocktailStatus = :cocktailStatus")
    fun findCocktailNameByCocktailStatus(@Param("cocktailName")cocktailName: String, @Param("cocktailStatus")cocktailStatus: Boolean): String?

    @Query(
        "select new com.lionTF.cshop.domain.admin.controller.dto.CocktailResultDTO(c.cocktailName, c.cocktailDescription)" +
            " from Cocktail c where c.cocktailId = :cocktailId")
    fun findCocktailById(@Param("cocktailId") cocktailId: Long): CocktailResultDTO


    fun countAllByCocktailStatusIsTrue(): Long
}