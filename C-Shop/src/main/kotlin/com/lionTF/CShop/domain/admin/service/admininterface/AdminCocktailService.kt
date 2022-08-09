package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailService {
    fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO

    fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO

    fun updateCocktail(createCocktailDTO: CreateCocktailDTO, cocktailId: Long): CreateCocktailResultDTO

    fun getAllCocktail(pageable: Pageable): Page<FindCocktails>
}