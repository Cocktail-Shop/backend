package com.lionTF.cshop.domain.shop.repository.custom

import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.models.Item
import org.springframework.stereotype.Repository

interface SearchRepositoryCustom {
    fun findAlcoholList(keyword: String) : List<Item>
    fun findNonAlcoholList(keyword: String) : List<Item>
    fun findCocktailList(keyword: String) : List<Cocktail>
}