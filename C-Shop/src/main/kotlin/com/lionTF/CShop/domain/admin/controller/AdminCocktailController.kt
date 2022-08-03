package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteCocktailResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import com.lionTF.CShop.domain.shop.controller.dto.CocktailResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.CocktailToCocktailDTO
import com.lionTF.CShop.domain.shop.controller.dto.setCocktailResultDTO
import com.lionTF.CShop.domain.shop.repository.CocktailRepository
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
class AdminCocktailController(
    private val adminCocktailService: AdminCocktailService,
    private var cocktailRepository: CocktailRepository,
) {

    // 칵테일 상품 등록
    @PostMapping("/admins/cocktails")
    fun createCocktail(@RequestBody createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO {
        return adminCocktailService.createCocktail(createCocktailDTO)
    }


    // 칵테일 단건 조회
    @GetMapping("/admins/cocktails/{cocktailId}")
    fun getCocktail(@PathVariable("cocktailId") cocktailId: Long): CocktailResultDTO {
        val cocktail = CocktailToCocktailDTO(cocktailRepository.getReferenceById(cocktailId))
        return setCocktailResultDTO(cocktail)
    }

    // 칵테일 삭제
    @DeleteMapping("/admins/cocktails")
    fun deleteCocktail(@RequestBody deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO {
        return adminCocktailService.deleteCocktail(deleteCocktailDTO)
    }
}