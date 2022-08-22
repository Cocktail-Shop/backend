package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.CocktailsDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailRepositoryCustom {

    fun findAllCocktails(pageable: Pageable): Page<CocktailsDTO>

    fun findCocktailsByName(cocktailName: String, pageable: Pageable): Page<CocktailsDTO>
}
