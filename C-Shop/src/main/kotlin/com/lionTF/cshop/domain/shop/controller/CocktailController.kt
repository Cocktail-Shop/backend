package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.shop.service.shopinterface.CocktailService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class CocktailController(
    private val cocktailService: CocktailService,
) {
    //칵테일 단건 조회 메소드
    @GetMapping(path=["/items/cocktails/{cocktailId}"])
    fun getCocktailById(@PathVariable("cocktailId") cocktailId: Long, model: Model): String {
        model.addAttribute("cocktail",cocktailService.findByCocktailId(cocktailId))
        return "shop/singleCocktail"
    }
}
