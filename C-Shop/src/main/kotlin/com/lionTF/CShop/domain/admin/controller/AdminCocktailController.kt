package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailItemService
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import com.lionTF.CShop.domain.shop.controller.dto.CocktailResultDTO
import com.lionTF.CShop.domain.shop.service.CocktailService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.LinkedHashMap

import org.springframework.web.bind.annotation.ModelAttribute

@Controller
@RequestMapping("/admins")
class AdminCocktailController(
    private val adminCocktailItemService: AdminCocktailItemService,
    private val adminCocktailService: AdminCocktailService,
    private val adminItemService: AdminItemService,
) {

    // 전체 칵테일 조회
    @GetMapping("all-cocktails")
    fun getCocktails(
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("cocktails", adminCocktailService.getAllCocktail(pageable))
        return "admins/cocktail/getAllCocktail"
    }

    // 칵테일 이름 검색 페이지
    @GetMapping("search/cocktails")
    fun getCocktailByName(
        @RequestParam("keyword") keyword: String,
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("cocktails", adminCocktailService.getCocktailsByName(keyword, pageable))
        return "admins/cocktail/getCocktailsByName"
    }

    // 칵테일 상품 등록 페이지
    @GetMapping("cocktails")
    fun getCreateCocktailForm(model: Model): String {
        model.addAttribute("createCocktailDTO", RequestCreateCocktailDTO.toFormDTO())
        return "admins/cocktail/createCocktailForm"
    }

    // 칵테일 상품 등록
    @PostMapping("cocktails")
    fun createCocktail(
        requestCreateCocktailDTO: RequestCreateCocktailDTO,
        model: Model
    ): String {
        model.addAttribute("result", adminCocktailService.createCocktail(requestCreateCocktailDTO))
        return "global/message"
    }


    // 칵테일 상세보기(수정) 페이지
    @GetMapping("cocktails/{cocktailId}")
    fun getCocktail(
        @PathVariable("cocktailId") cocktailId: Long,
        model: Model
    ): String {
        val itemIds = adminCocktailItemService.getItemIds(cocktailId)
        val cocktail = adminCocktailService.findCocktail(cocktailId, itemIds)
        model.addAttribute("cocktails", cocktail)
        model.addAttribute("cocktails", RequestUpdateCocktailDTO.formDTOFromResponseCocktailDTO(cocktail, itemIds))

        return "admins/cocktail/updateCocktailForm"
    }

    // 칵테일 수정
    //TODO
    @PutMapping("cocktails/{cocktailId}")
    fun updateCocktail(
        @PathVariable("cocktailId") cocktailId: Long,
        requestCreateCocktailDTO: RequestCreateCocktailDTO,
        model: Model
    ): String {
        model.addAttribute("result", adminCocktailService.updateCocktail(requestCreateCocktailDTO, cocktailId, requestCreateCocktailDTO.itemIds))
        return "global/message"
    }



    // 한개의 칵테일 삭제
    @DeleteMapping("cocktails/{cocktailId}")
    fun deleteOneCocktail(
        @PathVariable("cocktailId") cocktailId: Long,
        model: Model
    ): String {
        model.addAttribute("result", adminCocktailService.deleteOneCocktail(cocktailId))
        return "global/message"
    }


    @ModelAttribute("itemIds")
    fun favorite(pageable: Pageable): Map<Long, String> {
        var map: MutableMap<Long, String> = LinkedHashMap()
        var items = adminItemService.getAllItems(pageable)

        for (item in items.result!!.content) {
            map[item.itemId] = item.itemName
        }

        return map
    }

    // 한개 이상의 칵테일 삭제
//    @DeleteMapping("cocktails")
//    fun deleteCocktail(@RequestBody deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO {
//        return adminCocktailService.deleteCocktail(deleteCocktailDTO)
//    }
}