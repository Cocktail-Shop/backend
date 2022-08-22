package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.ItemsDTO
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

    override fun findAllItems(pageable: Pageable): Page<ItemsDTO> {
        val content: List<ItemsDTO> = contentInquire(pageable)

        val countQuery: JPAQuery<Item> = countInquire()

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    override fun findItemsByName(itemName: String, pageable: Pageable): Page<ItemsDTO> {
        val booleanBuilder = booleanBuilder(itemName)

        val content: List<ItemsDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<Item> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder? = null
    ): List<ItemsDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    ItemsDTO::class.java,
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
            .orderBy(item.createdAt.desc())
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
