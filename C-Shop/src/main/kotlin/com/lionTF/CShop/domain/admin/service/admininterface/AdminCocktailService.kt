package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminCocktailService {

    fun createCocktail(requestCreateCocktailDTO: RequestCreateCocktailDTO): AdminResponseDTO

    fun updateCocktail(requestCreateCocktailDTO: RequestCreateCocktailDTO, cocktailId: Long, itemIds: MutableList<Long>): AdminResponseDTO

    fun getAllCocktail(pageable: Pageable): ResponseSearchCocktailSearchDTO

    fun getCocktailsByName(keyword: String, pageable: Pageable): ResponseSearchCocktailSearchDTO

    fun findCocktail(cocktailId: Long, itemIds: MutableList<Long>): ResponseCocktailDTO

    fun deleteOneCocktail(cocktailId: Long): AdminResponseDTO

//    fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO
}