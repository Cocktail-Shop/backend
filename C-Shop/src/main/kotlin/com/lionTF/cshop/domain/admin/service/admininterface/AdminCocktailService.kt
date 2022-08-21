package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Cocktail
import org.springframework.data.domain.Pageable

interface AdminCocktailService {

    fun createCocktail(requestCreateCocktailDTO: CocktailCreateRequestDTO, cocktailImgUrl: String?): AdminResponseDTO

    fun updateCocktail(
        requestCreateCocktailDTO: CocktailCreateRequestDTO,
        cocktailId: Long, itemIds: MutableList<Long>,
        cocktailImgUrl: String?
    ): AdminResponseDTO

    fun getAllCocktail(pageable: Pageable): CocktailsSearchDTO

    fun getCocktailsByName(cocktailName: String, pageable: Pageable): CocktailsSearchDTO

    fun findCocktail(cocktailId: Long, itemIds: MutableList<Long>): CocktailResponseDTO

    fun deleteOneCocktail(cocktailId: Long): AdminResponseDTO

    fun findCocktailById(cocktailId: Long): Cocktail
}
