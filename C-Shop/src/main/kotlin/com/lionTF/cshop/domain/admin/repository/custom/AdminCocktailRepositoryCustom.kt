package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.FindCocktailsDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailRepositoryCustom {

    fun findAllCocktails(pageable: Pageable): Page<FindCocktailsDTO>

    fun findCocktailsByName(cocktailName: String, pageable: Pageable): Page<FindCocktailsDTO>
}
