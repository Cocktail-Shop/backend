package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailService {
    fun getAllCocktail(pageable: Pageable): Page<FindCocktails>

    fun getCocktailsByName(keyword: String, pageable: Pageable): Page<FindCocktails>

    fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO

    fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO

    fun deleteOneCocktail(cocktailId: Long): DeleteCocktailResultDTO

    fun updateCocktail(createCocktailDTO: CreateCocktailDTO, cocktailId: Long): CreateCocktailResultDTO
}