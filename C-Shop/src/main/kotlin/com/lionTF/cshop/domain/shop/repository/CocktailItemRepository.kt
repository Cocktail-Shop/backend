package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.admin.models.CocktailItem
import org.springframework.data.jpa.repository.JpaRepository

interface CocktailItemRepository : JpaRepository<CocktailItem, Long> {

}