package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.admin.models.Cocktail
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminCocktailRepository : JpaRepository<Cocktail, Long> {

    @Query("select ct.cocktailName from Cocktail ct where ct.cocktailName = :cocktailName and ct.cocktailStatus = :cocktailStatus")
    fun existsByCocktailName(@Param("cocktailName")cocktailName: String, @Param("cocktailStatus")cocktailStatus: Boolean): String?

}