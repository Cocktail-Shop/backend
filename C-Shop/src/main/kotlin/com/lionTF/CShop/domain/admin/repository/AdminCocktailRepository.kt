package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.admin.models.Cocktail
import org.springframework.data.jpa.repository.JpaRepository

interface AdminCocktailRepository : JpaRepository<Cocktail, Long> {

}