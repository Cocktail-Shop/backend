package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.shop.controller.dto.PageRequestDTO
import com.lionTF.CShop.domain.shop.service.PageSerivce
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

//상품 전체 조회 서비스(카테고리, 정렬 선택 가능)
@RestController
class PageController(
    private val pageSerivce: PageSerivce,
) {

    @GetMapping("/items")
    fun getPageList(pageRequestDTO: PageRequestDTO,@RequestParam("sort", defaultValue = "Name") sort: String, @RequestParam("category", defaultValue = "COCKTAIL") category: String): Any{
        //카테고리 default value로 cocktail설정, sort defalut value는 이름으로 설정
        //itemName과 cocktailName으로 필드명이 다르기 때문에 일단 Name으로 설정 후 카테고리 값에 따라 변경
        if(sort == "Name"){
            if(category == "COCKTAIL"){
                return pageSerivce.getPage(pageRequestDTO,"cocktailName",category)
            }
            else{
                return pageSerivce.getPage(pageRequestDTO,"itemName",category)
            }
        }
        else{
            return pageSerivce.getPage(pageRequestDTO,sort,category)
        }
    }
}