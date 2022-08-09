package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindCocktails
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailRepositoryCustom {

    fun findCocktailsInfo(keyword: String, pageable: Pageable): Page<FindCocktails>

    fun findAllByCocktailStatus(pageable: Pageable): Page<FindCocktails>
}