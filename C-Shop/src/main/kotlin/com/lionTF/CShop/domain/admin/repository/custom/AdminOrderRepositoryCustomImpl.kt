package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersDTO
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

class AdminOrderRepositoryCustomImpl(

    var queryFactory: JPAQueryFactory? = null

):AdminOrderRepositoryCustom {

    /**
     * 데이터 내용과 전체 카운트를 별도로 조회하는 방법을 이용하였습니다.
     */
    override fun findOrdersInfo(pageable: Pageable): Page<GetAllOrdersDTO> {

        // 데이터 내용을 조회하는 로직입니다.
        val content: List<GetAllOrdersDTO> = findOrder(null)

        // 카운트를 별도로 조회하여 데이터 내용과 같이 return 하는 로직입니다.
        return page(content, pageable, null)
    }

    // 4개의 테이블을 조인하여 회원의 ID로 주문 조회
    override fun findOrdersInfoByMemberId(keyword: String, pageable: Pageable): Page<GetAllOrdersDTO> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<GetAllOrdersDTO> = findOrder(booleanBuilder)

        return page(content, pageable, booleanBuilder!!)
    }

    // 데이터 내용을 조회하는 로직입니다.
    private fun findOrder(booleanBuilder: BooleanBuilder?): List<GetAllOrdersDTO> {
        val content: List<GetAllOrdersDTO> = queryFactory!!
            .select(
                Projections.constructor(
                    GetAllOrdersDTO::class.java,
                    orders.orderId,
                    orders.orderStatus,
                    item.itemId,
                    item.itemName,
                    item.price,
                    item.amount,
                    item.itemImgUrl,
                    member.memberId,
                    member.id,
                    member.memberName
                )
            ).from(orders, member)
            .leftJoin(orderItem).on(isEqualOrderId()).fetchJoin()
            .leftJoin(item).on(isEqualItemId()).fetchJoin()
            .where(
                isEqualMemberId(),
                booleanBuilder
            )
            .fetch()
        return content
    }

    // BooleanBuilder를 생성하는 함수입니다.
    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().and(member.id.contains(keyword))
    }

    // 카운트를 별도로 조회하여 데이터 내용과 같이 return 하는 로직입니다.
    private fun page(
        content: List<GetAllOrdersDTO>,
        pageable: Pageable,
        booleanBuilder: BooleanBuilder?
    ): Page<GetAllOrdersDTO> {
        val countQuery: JPAQuery<GetAllOrdersDTO> = queryFactory!!
            .select(
                Projections.constructor(
                    GetAllOrdersDTO::class.java,
                    orders.orderId,
                    orders.orderStatus,
                    item.itemId,
                    item.itemName,
                    item.price,
                    item.amount,
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
                booleanBuilder
            )

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    private fun isEqualOrderId() = orders.orderId.eq(orderItem.orders.orderId)

    private fun isEqualItemId() = orderItem.item.itemId.eq(item.itemId)

    private fun isEqualMemberId() = member.memberId.eq(orders.member.memberId)
}