package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailResultDTO

interface AdminCocktailService {
    fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO

    fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO

    fun updateCocktail(createCocktailDTO: CreateCocktailDTO, cocktailId: Long): CreateCocktailResultDTO
}