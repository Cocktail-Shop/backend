package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.service.shopinterface.PageService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


@Controller
class SearchController(
    private val pageService: PageService,
) {
    @GetMapping(path = ["/items/search/{category}"])
    fun searchItem(
        @PathVariable("category") category: String,
        @RequestParam("keyword") keyword: String,
        pageRequestDTO: PageRequestDTO,
        model: Model
    ): String {

        return if (category == Category.COCKTAIL.toString()) {
            model.addAttribute(
                "cocktails",
                pageService.getSearchPage(pageRequestDTO, "cocktailName", category, keyword)
            )
            "shop/shopSearchCocktail"
        } else {
            if (category == Category.ALCOHOL.toString()) {
                model.addAttribute("items", pageService.getSearchPage(pageRequestDTO, "itemName", category, keyword))
                "shop/shopSearchAlcohol"
            } else {
                model.addAttribute("items", pageService.getSearchPage(pageRequestDTO, "itemName", category, keyword))
                "shop/shopSearchNonAlcohol"
            }
        }
    }
}
