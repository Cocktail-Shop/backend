package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.models.QCocktail.cocktail
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.repository.CocktailRepository
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service

@Service
class CocktailService (
    private var cocktailRepository: CocktailRepository,
    private val queryFactory: JPAQueryFactory,
){

    //칵테일 단건 조회 서비스 구현
    fun findByCocktailId(cocktailId: Long): CocktailResultDTO {
        val cocktail = cocktailToCocktailDTO(cocktailRepository.getReferenceById(cocktailId))
        return setCocktailResultDTO(cocktail)
    }


}