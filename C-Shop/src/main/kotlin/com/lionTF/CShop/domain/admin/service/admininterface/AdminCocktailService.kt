package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailResultDTO
import com.lionTF.CShop.domain.admin.models.Item
import java.util.*

interface AdminCocktailService {
    fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO

    fun existedCocktail(createCocktailDTO: CreateCocktailDTO): String?

    fun existedItem(itemId: Long): Optional<Item>

    fun formToExistedItems(itemList: MutableList<Long>): Boolean

    fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO
}