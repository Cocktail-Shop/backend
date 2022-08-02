package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO

interface AdminCocktailService {
    fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO
}