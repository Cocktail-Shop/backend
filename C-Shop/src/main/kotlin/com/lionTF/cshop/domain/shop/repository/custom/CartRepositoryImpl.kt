package com.lionTF.cshop.domain.shop.repository.custom

import com.lionTF.cshop.domain.admin.models.QItem.item
import com.lionTF.cshop.domain.member.models.QMember.member
import com.lionTF.cshop.domain.shop.controller.dto.FindCartDTO
import com.lionTF.cshop.domain.shop.models.QCart.cart
import com.lionTF.cshop.domain.shop.models.QCartItem.cartItem
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class CartRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

):CartRepositoryCustom {

    // 장바구니 상품 조회
    override fun findCartInfo(pageable: Pageable): Page<FindCartDTO> {

        val content: List<FindCartDTO> = contentInquire(pageable)
        val countQuery: JPAQuery<FindCartDTO> = countInquire()
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable
    ): List<FindCartDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindCartDTO::class.java,
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
                isExistedMember()
            )
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(
    ): JPAQuery<FindCartDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindCartDTO::class.java,
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
                isEqualMemberId(),
                isExistedMember()
            )
    }

    private fun isEqualCartId() = cart.cartId.eq(cartItem.cart.cartId)

    private fun isEqualItemId() = cartItem.item.itemId.eq(item.itemId)

    private fun isEqualMemberId() = member.memberId.eq(cart.member.memberId)

    private fun isExistedMember() = member.memberStatus.eq(true)
}