package com.lionTF.CShop.domain.shop.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindOrdersDTO
import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.member.models.QMember.member
import com.lionTF.CShop.domain.shop.models.QOrderItem.orderItem
import com.lionTF.CShop.domain.shop.models.QOrders.orders
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class OrderRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

):OrderRepositoryCustom {

    // 회원의 ID로 주문 조회
    override fun findOrdersInfo(pageable: Pageable): Page<FindOrdersDTO> {

        val content: List<FindOrdersDTO> = contentInquire(pageable)
        val countQuery: JPAQuery<FindOrdersDTO> = countInquire()

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable
    ): List<FindOrdersDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindOrdersDTO::class.java,
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
    ): JPAQuery<FindOrdersDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindOrdersDTO::class.java,
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