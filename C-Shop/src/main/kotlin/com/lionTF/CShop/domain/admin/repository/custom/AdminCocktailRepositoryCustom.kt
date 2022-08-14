package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindCocktailsDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailRepositoryCustom {

    fun findAllCocktails(pageable: Pageable): Page<FindCocktailsDTO>

    fun findCocktailsByName(keyword: String, pageable: Pageable): Page<FindCocktailsDTO>
}