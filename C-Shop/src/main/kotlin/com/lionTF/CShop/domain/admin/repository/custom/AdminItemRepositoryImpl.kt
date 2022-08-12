package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindItemDTO
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.models.QItem.item
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Page
import org.springframework.data.support.PageableExecutionUtils


class AdminItemRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

) : AdminItemRepositoryCustom {

    // 칵테일 전체 조회
    override fun findAllItems(pageable: Pageable): Page<FindItemDTO> {
        // 데이터 내용을 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val content: List<FindItemDTO> = contentInquire(pageable, null)

        // 카운트를 별도로 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val countQuery: JPAQuery<Item> = countInquire(null)

        // 위에서 반환된 데이터 내용과 카운트를 반환합니다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 회원 ID로 회원 검색
    override fun findItemsByName(keyword: String, pageable: Pageable): Page<FindItemDTO> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<FindItemDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<Item> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder?
    ): List<FindItemDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindItemDTO::class.java,
                    item.itemId,
                    item.itemName,
                    item.price,
                    item.amount,
                    item.degree,
                    item.itemDescription,
                    item.itemImgUrl,
                    item.category,
                    item.createdAt
                )
            )
            .from(item)
            .where(
                booleanBuilder,
                isEqualCocktailStatus()
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(booleanBuilder: BooleanBuilder?): JPAQuery<Item> {
        return queryFactory!!
            .selectFrom(item)
            .where(
                booleanBuilder,
                isEqualCocktailStatus()
            )
    }

    // BooleanBuilder를 생성하는 함수입니다.
    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().and(item.itemName.contains(keyword))
    }

    private fun isEqualCocktailStatus() = item.itemStatus.eq(true)
}