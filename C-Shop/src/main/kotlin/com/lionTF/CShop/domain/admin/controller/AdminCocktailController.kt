package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class AdminCocktailController(
    private val adminCocktailService: AdminCocktailService,
) {

    // 칵테일 상품 등록
    @PostMapping("/admins/cocktails")
    fun createCocktail(@RequestBody createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO {
        return adminCocktailService.createCocktail(createCocktailDTO)
    }
}