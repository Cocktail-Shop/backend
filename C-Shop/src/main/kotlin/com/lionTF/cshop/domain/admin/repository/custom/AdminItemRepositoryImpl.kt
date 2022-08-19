package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.FindItemDTO
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.models.QItem.item
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
        val content: List<FindItemDTO> = contentInquire(pageable)

        val countQuery: JPAQuery<Item> = countInquire()

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 회원 ID로 회원 검색
    override fun findItemsByName(itemName: String, pageable: Pageable): Page<FindItemDTO> {
        val booleanBuilder = booleanBuilder(itemName)

        val content: List<FindItemDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<Item> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder? = null
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
    private fun countInquire(
        booleanBuilder: BooleanBuilder? = null
    ): JPAQuery<Item> {
        return queryFactory!!
            .selectFrom(item)
            .where(
                booleanBuilder,
                isEqualCocktailStatus()
            )
    }

    private fun booleanBuilder(itemName: String): BooleanBuilder? {
        return BooleanBuilder().and(item.itemName.contains(itemName))
    }

    private fun isEqualCocktailStatus() = item.itemStatus.eq(true)
}
