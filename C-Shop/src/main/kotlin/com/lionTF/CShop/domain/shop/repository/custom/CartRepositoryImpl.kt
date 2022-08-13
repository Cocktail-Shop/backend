package com.lionTF.CShop.domain.shop.repository.custom

import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.member.models.QMember.member
import com.lionTF.CShop.domain.shop.controller.dto.FindCartDTO
import com.lionTF.CShop.domain.shop.models.QOrderItem.orderItem
import com.lionTF.CShop.domain.shop.models.QOrders.orders
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
                    orders.orderId,
                    orders.orderStatus,
                    item.itemId,
                    item.itemName,
                    item.price,
                    orderItem.amount,
                    orderItem.createdAt,
                    item.itemImgUrl,
                    member.memberId,
                    member.id,
                    member.memberName
                )
            ).from(orders, member)
            .leftJoin(orderItem).on(isEqualOrderId()).fetchJoin()
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
                    orders.orderId,
                    orders.orderStatus,
                    item.itemId,
                    item.itemName,
                    item.price,
                    orderItem.amount,
                    orderItem.createdAt,
                    item.itemImgUrl,
                    member.memberId,
                    member.id,
                    member.memberName
                )
            )
            .from(orders, member)
            .leftJoin(orderItem).on(isEqualOrderId()).fetchJoin()
            .leftJoin(item).on(isEqualItemId()).fetchJoin()
            .where(
                isEqualMemberId(),
                isExistedMember()
            )
    }

    private fun isEqualOrderId() = orders.orderId.eq(orderItem.orders.orderId)

    private fun isEqualItemId() = orderItem.item.itemId.eq(item.itemId)

    private fun isEqualMemberId() = member.memberId.eq(orders.member.memberId)

    private fun isExistedMember() = member.memberStatus.eq(true)
}