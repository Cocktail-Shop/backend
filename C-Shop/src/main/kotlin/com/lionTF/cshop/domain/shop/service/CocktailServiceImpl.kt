package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.repository.CocktailRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.CocktailService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class CocktailServiceImpl(
    private var cocktailRepository: CocktailRepository,
) : CocktailService {

    override fun findByCocktailId(cocktailId: Long): CocktailResultDTO {
        //val cocktail = CocktailDTO.fromCocktail(cocktailRepository.getReferenceById(cocktailId))
        val cocktail = CocktailDTO.fromCocktail(cocktailRepository.getCocktailItemCustom(cocktailId))

        return CocktailResultDTO.setCocktailResultDTO(cocktail)
    }
}
