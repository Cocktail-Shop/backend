package com.lionTF.cshop.domain.shop.repository.custom

import com.lionTF.cshop.domain.admin.models.*
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class SearchRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
) : SearchRepositoryCustom {

    override fun findAlcoholList(keyword: String): List<Item> {
        val booleanBuilder = BooleanBuilder()
        booleanBuilder.and(QItem.item.itemName.contains(keyword))
        booleanBuilder.and(QItem.item.category.eq(Category.ALCOHOL))
        booleanBuilder.and(QItem.item.itemStatus.isTrue)
        return queryFactory.selectFrom(QItem.item).where(booleanBuilder).fetch()
    }

    override fun findNonAlcoholList(keyword: String): List<Item> {
        val booleanBuilder = BooleanBuilder()
        booleanBuilder.and(QItem.item.itemName.contains(keyword))
        booleanBuilder.and(QItem.item.category.eq(Category.NONALCOHOL))
        booleanBuilder.and(QItem.item.itemStatus.isTrue)
        return queryFactory.selectFrom(QItem.item).where(booleanBuilder).fetch()
    }

    override fun findCocktailList(keyword: String): List<Cocktail> {
        val booleanBuilder = BooleanBuilder()
        booleanBuilder.and(QCocktail.cocktail.cocktailName.contains(keyword))
        booleanBuilder.and(QCocktail.cocktail.cocktailStatus.isTrue)
        return queryFactory.selectFrom(QCocktail.cocktail).where(booleanBuilder).fetch()
    }
}
