package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.admin.models.CocktailItem
import org.springframework.data.jpa.repository.JpaRepository

interface AdminCocktailItemRepository : JpaRepository<CocktailItem, Long> {

}