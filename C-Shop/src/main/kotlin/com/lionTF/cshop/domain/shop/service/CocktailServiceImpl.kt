package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.repository.CocktailRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.CocktailService
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service

@Service
class CocktailServiceImpl (
    private var cocktailRepository: CocktailRepository,
    private val queryFactory: JPAQueryFactory,
): CocktailService{
    //칵테일 단건 조회 서비스 구현
    override fun findByCocktailId(cocktailId: Long): CocktailResultDTO {
        val cocktail = CocktailDTO.fromCocktail(cocktailRepository.getReferenceById(cocktailId))
        return CocktailResultDTO.setCocktailResultDTO(cocktail)
    }
}