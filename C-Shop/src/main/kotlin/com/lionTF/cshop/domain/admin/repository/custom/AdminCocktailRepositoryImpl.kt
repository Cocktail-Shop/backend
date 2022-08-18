package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.FindCocktailsDTO
import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.models.QCocktail.cocktail
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Page
import org.springframework.data.support.PageableExecutionUtils


class AdminCocktailRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

) : AdminCocktailRepositoryCustom {

    // 칵테일 전체 조회
    override fun findAllCocktails(pageable: Pageable): Page<FindCocktailsDTO> {
        val content: List<FindCocktailsDTO> = contentInquire(pageable)

        val countQuery: JPAQuery<Cocktail> = countInquire()

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 회원 ID로 회원 검색
    override fun findCocktailsByName(keyword: String, pageable: Pageable): Page<FindCocktailsDTO> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<FindCocktailsDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<Cocktail> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder? = null
    ): List<FindCocktailsDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindCocktailsDTO::class.java,
                    cocktail.cocktailId,
                    cocktail.cocktailName,
                    cocktail.cocktailDescription,
                    cocktail.cocktailImgUrl,
                    cocktail.createdAt
                )
            )
            .from(cocktail)
            .where(
                booleanBuilder,
                isEqualCocktailStatus()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(
        booleanBuilder: BooleanBuilder? = null
    ): JPAQuery<Cocktail> {
        return queryFactory!!
            .selectFrom(cocktail)
            .where(
                booleanBuilder,
                isEqualCocktailStatus()
            )
    }

    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().and(cocktail.cocktailName.contains(keyword))
    }

    private fun isEqualCocktailStatus() = cocktail.cocktailStatus.eq(true)
}
