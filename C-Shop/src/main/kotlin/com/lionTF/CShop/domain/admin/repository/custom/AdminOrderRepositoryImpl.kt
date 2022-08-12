package com.lionTF.CShop.domain.admin.repository.custom

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

class AdminOrderRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

):AdminOrderRepositoryCustom {

    /**
     * 데이터 내용과 전체 카운트를 별도로 조회하는 방법을 이용하였습니다.
     */
    override fun findOrdersInfo(pageable: Pageable): Page<FindOrdersDTO> {
        // 데이터 내용을 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val content: List<FindOrdersDTO> = contentInquire(pageable, null)

        // 카운트를 별도로 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val countQuery: JPAQuery<FindOrdersDTO> = countInquire(null)

        // 위에서 반환된 데이터 내용과 카운트를 반환합니다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 회원의 ID로 주문 조회
    override fun findOrdersInfoByMemberId(keyword: String, pageable: Pageable): Page<FindOrdersDTO> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<FindOrdersDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<FindOrdersDTO> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder?
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
                booleanBuilder,
                isExistedMember()
            )
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(
        booleanBuilder: BooleanBuilder?
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
                booleanBuilder,
                isEqualMemberId(),
                isExistedMember()
            )
    }

    // BooleanBuilder를 생성하는 함수입니다.
    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().and(member.id.contains(keyword))
    }

    private fun isEqualOrderId() = orders.orderId.eq(orderItem.orders.orderId)

    private fun isEqualItemId() = orderItem.item.itemId.eq(item.itemId)

    private fun isEqualMemberId() = member.memberId.eq(orders.member.memberId)

    private fun isExistedMember() = member.memberStatus.eq(true)
}