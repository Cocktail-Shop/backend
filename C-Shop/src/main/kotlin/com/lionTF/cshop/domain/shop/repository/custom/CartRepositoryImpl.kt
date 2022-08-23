package com.lionTF.cshop.domain.shop.repository.custom

import com.lionTF.cshop.domain.admin.models.QItem.item
import com.lionTF.cshop.domain.member.models.QMember.member
import com.lionTF.cshop.domain.shop.controller.dto.FindCartDTO
import com.lionTF.cshop.domain.shop.models.QCart.cart
import com.lionTF.cshop.domain.shop.models.QCartItem.cartItem
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class CartRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

) : CartRepositoryCustom {

    override fun findCartInfo(memberId: Long, pageable: Pageable): Page<FindCartDTO> {
        val booleanBuilder = booleanBuilderId(memberId)

        val content: List<FindCartDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<FindCartDTO> = countInquire(booleanBuilder)
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder? = null
    ): List<FindCartDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindCartDTO::class.java,
                    cartItem.cartItemId,
                    cart.cartId,
                    item.itemId,
                    item.itemName,
                    item.price,
                    cartItem.item,
                    cartItem.amount,
                    item.itemImgUrl,
                    member.memberId
                )
            ).from(cart, member)
            .leftJoin(cartItem).on(isEqualCartId()).fetchJoin()
            .leftJoin(item).on(isEqualItemId()).fetchJoin()
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .where(
                isEqualMemberId(),
                booleanBuilder,
                isExistedMember()
            )
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(
        booleanBuilder: BooleanBuilder? = null
    ): JPAQuery<FindCartDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindCartDTO::class.java,
                    cartItem.cartItemId,
                    cart.cartId,
                    item.itemId,
                    item.itemName,
                    item.price,
                    cartItem.item,
                    cartItem.amount,
                    item.itemImgUrl,
                    member.memberId
                )
            )
            .from(cart, member)
            .leftJoin(cartItem).on(isEqualCartId()).fetchJoin()
            .leftJoin(item).on(isEqualItemId()).fetchJoin()
            .where(
                booleanBuilder,
                isEqualMemberId(),
                isExistedMember()
            )
    }

    private fun booleanBuilderId(memberId: Long): BooleanBuilder? {
        return BooleanBuilder().or(member.memberId.eq(memberId))
    }

    private fun isEqualCartId() = cart.cartId.eq(cartItem.cart.cartId)

    private fun isEqualItemId() = cartItem.item.itemId.eq(item.itemId)

    private fun isEqualMemberId() = member.memberId.eq(cart.member.memberId)

    private fun isExistedMember() = member.memberStatus.eq(true)
}
