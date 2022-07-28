package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.shop.controller.dto.CocktailResultDTO
import com.lionTF.CShop.domain.shop.service.CocktailService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.PathVariable


@RestController
class CocktailController(
    private val cocktailService: CocktailService,
) {
    //칵테일 단건 조회 메소드
    @GetMapping(path=["/items/cocktail/{cocktailId}"])
    fun getCocktailById(@PathVariable("cocktailId") cocktailId: Long): CocktailResultDTO {
        return cocktailService.findByCocktailId(cocktailId)
    }
}