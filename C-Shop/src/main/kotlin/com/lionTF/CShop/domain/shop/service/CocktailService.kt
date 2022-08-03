package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.repository.CocktailItemRepository
import com.lionTF.CShop.domain.shop.repository.CocktailRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CocktailService (
    private var cocktailRepository: CocktailRepository,
){

    //칵테일 단건 조회 서비스 구현
    fun findByCocktailId(cocktailId: Long): CocktailResultDTO {
        val cocktail: CocktailDTO
        cocktail = cocktailToCocktailDTO(cocktailRepository.getReferenceById(cocktailId))
        return setCocktailResultDTO(cocktail)
    }

}