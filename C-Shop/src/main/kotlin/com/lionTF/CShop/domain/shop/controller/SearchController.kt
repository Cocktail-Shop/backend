package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.service.CocktailService
import com.lionTF.CShop.domain.shop.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class SearchController(
    private val itemService: ItemService,
    private val cocktailService: CocktailService,
) {
    @GetMapping(path=["/items/search/{category}"])
    fun searchItem(@PathVariable("category") category: String, @RequestParam("keyword") keyword: String) : Any {
        if(category == Category.COCKTAIL.toString()){
            val data: List<SearchCocktailInfoDTO> = cocktailService.getDataList(keyword)
            val result = SearchCocktailDTO(keyword,data)
            return SearchCocktailResultDTO(
                HttpStatus.OK.value(),
                "칵테일 검색 성공",
                result
            )
        }
        else{
            val data: List<SearchItemInfoDTO> = itemService.getDataList(keyword)
            val result = SearchItemDTO(keyword,data)
            return SearchItemResultDTO(
                HttpStatus.OK.value(),
                "상품 검색 성공",
                result
            )
        }

    }

}