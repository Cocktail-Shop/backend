package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.OrdersDTO
import com.lionTF.cshop.domain.admin.models.QItem.item
import com.lionTF.cshop.domain.member.models.QMember.member
import com.lionTF.cshop.domain.shop.models.QOrderItem.orderItem
import com.lionTF.cshop.domain.shop.models.QOrders.orders
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class AdminOrderRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

) : AdminOrderRepositoryCustom {

    override fun findOrdersInfo(pageable: Pageable): Page<OrdersDTO> {
        val content: List<OrdersDTO> = contentInquire(pageable)

        val countQuery: JPAQuery<OrdersDTO> = countInquire()

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    override fun findOrdersInfoByMemberId(keyword: String, pageable: Pageable): Page<OrdersDTO> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<OrdersDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<OrdersDTO> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder? = null
    ): List<OrdersDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    OrdersDTO::class.java,
                    orders.orderId,
                    orders.orderStatus,
                    orders.deliveryStatus,
                    orders.orderAddress,
                    orders.orderAddressDetail,
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
            .where(
                isEqualMemberId(),
                booleanBuilder,
                isExistedMember()
            )
            .orderBy(orderItem.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(
        booleanBuilder: BooleanBuilder? = null
    ): JPAQuery<OrdersDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    OrdersDTO::class.java,
                    orders.orderId,
                    orders.orderStatus,
                    orders.deliveryStatus,
                    orders.orderAddress,
                    orders.orderAddressDetail,
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

    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().or(member.id.contains(keyword)).or(item.itemName.contains(keyword))
    }

    private fun isEqualOrderId() = orders.orderId.eq(orderItem.orders.orderId)

    private fun isEqualItemId() = orderItem.item.itemId.eq(item.itemId)

    private fun isEqualMemberId() = member.memberId.eq(orders.member.memberId)

    private fun isExistedMember() = member.memberStatus.eq(true)
}
