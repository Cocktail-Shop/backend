package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import com.lionTF.CShop.domain.shop.controller.dto.CocktailResultDTO
import com.lionTF.CShop.domain.shop.service.CocktailService
import org.springframework.web.bind.annotation.*

@RestController
class AdminCocktailController(
    private val adminCocktailService: AdminCocktailService,

    private val cocktailService: CocktailService,
) {

    // 칵테일 상품 등록
    @PostMapping("/admins/cocktails")
    fun createCocktail(@RequestBody createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO {
        return adminCocktailService.createCocktail(createCocktailDTO)
    }


    // 칵테일 단건 조회
    @GetMapping("/admins/cocktails/{cocktailId}")
    fun getCocktail(@PathVariable("cocktailId") cocktailId: Long): CocktailResultDTO {
        return cocktailService.findByCocktailId(cocktailId)
    }

    // 칵테일 삭제
    @DeleteMapping("/admins/cocktails")
    fun deleteCocktail(@RequestBody deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO {
        return adminCocktailService.deleteCocktail(deleteCocktailDTO)
    }


    // 칵테일 수정
   @PutMapping("/admins/cocktails/{cocktailId}")
    fun updateCocktail(
        @PathVariable("cocktailId") cocktailId: Long,
        @RequestBody createCocktailDTO: CreateCocktailDTO
    ): CreateCocktailResultDTO {
        return adminCocktailService.updateCocktail(createCocktailDTO, cocktailId)
    }
}