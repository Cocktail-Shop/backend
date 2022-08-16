package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.shop.controller.dto.CocktailResultDTO

interface CocktailService {
    //칵테일 단건 조회 서비스 구현
    fun findByCocktailId(cocktailId: Long): CocktailResultDTO
}