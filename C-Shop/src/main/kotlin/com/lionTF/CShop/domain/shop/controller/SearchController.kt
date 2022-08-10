package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.service.CocktailService
import com.lionTF.CShop.domain.shop.service.ItemService
import com.lionTF.CShop.domain.shop.service.PageSerivce
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@Controller
class SearchController(
    private val itemService: ItemService,
    private val cocktailService: CocktailService,
    private val pageSerivce: PageSerivce,
) {
    @GetMapping(path=["/items/search/{category}"])
    fun searchItem(pageRequestDTO: PageRequestDTO,model: Model,@PathVariable("category") category: String, @RequestParam("keyword") keyword: String) : String {
        if(category == Category.COCKTAIL.toString()){
            model.addAttribute("cocktails",pageSerivce.getSearchPage(pageRequestDTO,"cocktailName",category,keyword))
            return "shop/shopSearchCocktail"
        }
        else{
            if(category==Category.ALCOHOL.toString()){
                model.addAttribute("items",pageSerivce.getSearchPage(pageRequestDTO,"itemName",category,keyword))
                return "shop/shopSearchAlcohol"
            }
            else{
                model.addAttribute("items",pageSerivce.getSearchPage(pageRequestDTO,"itemName",category,keyword))
                return "shop/shopSearchNonAlcohol"
            }
        }

    }

}