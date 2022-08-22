package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.shop.controller.dto.CocktailResultDTO

interface CocktailService {

    fun findByCocktailId(cocktailId: Long): CocktailResultDTO
}
