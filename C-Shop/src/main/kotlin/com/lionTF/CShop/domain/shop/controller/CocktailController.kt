package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
import com.lionTF.CShop.domain.shop.service.CocktailService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.web.bind.annotation.PathVariable


@RestController
class CocktailController(
    private val cocktailService: CocktailService,
) {
    //칵테일 단건 조회 메소드
    @GetMapping(path=["/items/cocktail/{cocktailId}"])
    fun getCocktailById(@PathVariable("cocktailId") cocktailId: Long): ReadCocktailResultDTO {
        return cocktailService.findByCocktailId(cocktailId)
    }
}
