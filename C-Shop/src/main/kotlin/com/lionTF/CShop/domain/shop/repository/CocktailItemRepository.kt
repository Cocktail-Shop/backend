package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.admin.models.CocktailItem
import org.springframework.data.jpa.repository.JpaRepository

interface CocktailItemRepository : JpaRepository<CocktailItem, Long> {

}