package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindCocktails
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.QCocktail.cocktail
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
    override fun findAllCocktails(pageable: Pageable): Page<FindCocktails> {
        // 데이터 내용을 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val content: List<FindCocktails> = contentInquire(pageable, null)

        // 카운트를 별도로 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val countQuery: JPAQuery<Cocktail> = countInquire(null)

        // 위에서 반환된 데이터 내용과 카운트를 반환합니다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 회원 ID로 회원 검색
    override fun findCocktailsByName(keyword: String, pageable: Pageable): Page<FindCocktails> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<FindCocktails> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<Cocktail> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder?
    ): List<FindCocktails> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindCocktails::class.java,
                    cocktail.cocktailName,
                    cocktail.cocktailDescription.substring(0, 30),
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
    private fun countInquire(booleanBuilder: BooleanBuilder?): JPAQuery<Cocktail> {
        return queryFactory!!
            .selectFrom(cocktail)
            .where(
                booleanBuilder,
                isEqualCocktailStatus()
            )
    }

    // BooleanBuilder를 생성하는 함수입니다.
    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().and(cocktail.cocktailName.contains(keyword))
    }

    private fun isEqualCocktailStatus() = cocktail.cocktailStatus.eq(true)
}