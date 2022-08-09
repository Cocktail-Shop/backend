package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import com.lionTF.CShop.domain.shop.controller.dto.CocktailResultDTO
import com.lionTF.CShop.domain.shop.service.CocktailService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/admins")
class AdminCocktailController(
    private val adminCocktailService: AdminCocktailService,

    private val cocktailService: CocktailService,
) {

    // 전체 칵테일 조회
    @GetMapping("all-cocktails")
    fun getCocktails(
        model: Model,
        @PageableDefault(size = 2) pageable: Pageable
    ): String {
        model.addAttribute("cocktails", adminCocktailService.getAllCocktail(pageable))
        return "admins/cocktail/getAllCocktail"
    }

    // 칵테일 상품 등록 페이지
    @GetMapping("cocktails")
    fun getCreateCocktailForm(model: Model): String {
        val createCocktailDTO = CreateCocktailDTO()
        model.addAttribute("form", createCocktailDTO)

        return "admins/cocktail/createCocktailForm"
    }

    // 칵테일 상품 등록
    @PostMapping("cocktails")
    fun createCocktail(@RequestBody createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO {
        return adminCocktailService.createCocktail(createCocktailDTO)
    }


    // 칵테일 단건 조회
    @GetMapping("cocktails/{cocktailId}")
    fun getCocktail(@PathVariable("cocktailId") cocktailId: Long): CocktailResultDTO {
        return cocktailService.findByCocktailId(cocktailId)
    }

    // 칵테일 삭제
    @DeleteMapping("cocktails")
    fun deleteCocktail(@RequestBody deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO {
        return adminCocktailService.deleteCocktail(deleteCocktailDTO)
    }


    // 칵테일 수정
   @PutMapping("cocktails/{cocktailId}")
    fun updateCocktail(
        @PathVariable("cocktailId") cocktailId: Long,
        @RequestBody createCocktailDTO: CreateCocktailDTO
    ): CreateCocktailResultDTO {
        return adminCocktailService.updateCocktail(createCocktailDTO, cocktailId)
    }
}