package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Cocktail
import org.springframework.data.domain.Pageable

interface AdminCocktailService {

    fun createCocktail(requestCreateCocktailDTO: RequestCreateCocktailDTO, cocktailImgUrl: String?): AdminResponseDTO

    fun updateCocktail(
        requestCreateCocktailDTO: RequestCreateCocktailDTO,
        cocktailId: Long, itemIds: MutableList<Long>,
        cocktailImgUrl: String?
    ): AdminResponseDTO

    fun getAllCocktail(pageable: Pageable): ResponseSearchCocktailSearchDTO

    fun getCocktailsByName(keyword: String, pageable: Pageable): ResponseSearchCocktailSearchDTO

    fun findCocktail(cocktailId: Long, itemIds: MutableList<Long>): ResponseCocktailDTO

    fun deleteOneCocktail(cocktailId: Long): AdminResponseDTO

    fun findCocktailById(cocktailId: Long): Cocktail

//    fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO
}
