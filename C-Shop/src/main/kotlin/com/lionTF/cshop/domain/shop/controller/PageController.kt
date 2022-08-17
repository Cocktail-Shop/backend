package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.shop.controller.dto.PageRequestDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.PageSerivce
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

//상품 전체 조회 서비스(카테고리, 정렬 선택 가능)
@Controller
class PageController(
    private val pageSerivce: PageSerivce,
) {

    @GetMapping("/items")
    fun getPageList(pageRequestDTO: PageRequestDTO, model: Model, @RequestParam("sort", defaultValue = "Name") sort: String, @RequestParam("category", defaultValue = "COCKTAIL") category: String): String{
        //카테고리 default value로 cocktail설정, sort defalut value는 이름으로 설정
        //itemName과 cocktailName으로 필드명이 다르기 때문에 일단 Name으로 설정 후 카테고리 값에 따라 변경
        if(sort == "Name"){
            if(category == "COCKTAIL"){
                model.addAttribute("cocktails", pageSerivce.getPage(pageRequestDTO,"cocktailName",category))
            }
            else{
                model.addAttribute("items",pageSerivce.getPage(pageRequestDTO,"itemName",category))
            }
        }
        else{
            if(category=="COCKTAIL"){
                model.addAttribute("cocktails",pageSerivce.getPage(pageRequestDTO,sort,category))
            }
            else{
                model.addAttribute("items",pageSerivce.getPage(pageRequestDTO,sort,category))
            }
        }

        if(category=="COCKTAIL"){
            return "shop/shopCocktail"
        }
        else if(category=="ALCOHOL"){
            return "shop/shopAlcohol"
        }
        else{
            return "shop/shopNonAlcohol"
        }
    }
}
