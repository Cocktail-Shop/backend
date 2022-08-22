package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Cocktail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface CocktailRepository : JpaRepository<Cocktail, Long>, QuerydslPredicateExecutor<Cocktail> {

    fun findCocktailByCategoryAndCocktailStatus(category: Category, status: Boolean, pageable: Pageable): Page<Cocktail>
}
