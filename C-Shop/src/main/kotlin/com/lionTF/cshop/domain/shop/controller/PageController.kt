package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.shop.controller.dto.PageRequestDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.PageService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PageController(
    private val pageService: PageService,
) {

    @GetMapping("/items")
    fun getPageList(
        @RequestParam("category", defaultValue = "COCKTAIL") category: String,
        @RequestParam("sort", defaultValue = "Name") sort: String,
        pageRequestDTO: PageRequestDTO,
        model: Model
    ): String {

        //카테고리 default value 로 cocktail 설정, sort default value 는 이름으로 설정
        //itemName 과 cocktailName 으로 필드명이 다르기 때문에 일단 Name 으로 설정 후 카테고리 값에 따라 변경
        if (sort == "Name") {
            if (category == "COCKTAIL") {
                model.addAttribute("cocktails", pageService.getPage(pageRequestDTO, "cocktailName", category))
            } else {
                model.addAttribute("items", pageService.getPage(pageRequestDTO, "itemName", category))
            }
        } else {
            if (category == "COCKTAIL") {
                model.addAttribute("cocktails", pageService.getPage(pageRequestDTO, sort, category))
            } else {
                model.addAttribute("items", pageService.getPage(pageRequestDTO, sort, category))
            }
        }

        return when (category) {
            "COCKTAIL" -> {
                "shop/shopCocktail"
            }
            "ALCOHOL" -> {
                "shop/shopAlcohol"
            }
            else -> {
                "shop/shopNonAlcohol"
            }
        }
    }
}
