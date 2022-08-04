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

    //칵테일 검색, 키워드가 포함되어 있는 칵테일 찾기
    fun getDataList(keyword: String) : List<SearchCocktailInfoDTO>{
        val booleanBuilder: BooleanBuilder = BooleanBuilder()
        booleanBuilder.and(cocktail.cocktailName.contains(keyword))
        val cocktailList = queryFactory.selectFrom(cocktail).where(booleanBuilder).fetch()
        val cocktailDTOList: MutableList<SearchCocktailInfoDTO> = mutableListOf()

        for(cocktail in cocktailList){
            cocktailDTOList.add(cocktailToSearchCocktailInfoDTO(cocktail))
        }

        return cocktailDTOList
    }

}