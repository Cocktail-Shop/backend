package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.apache.ibatis.annotations.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor

@Repository
interface CocktailRepository : JpaRepository<Cocktail, Long>, QuerydslPredicateExecutor<Cocktail> {
    fun findCocktailByCategoryAndCocktailStatus(category: Category,status: Boolean, pageable: Pageable) : Page<Cocktail>
}
