package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersDTO
import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.member.models.QMember.member
import com.lionTF.CShop.domain.shop.models.QOrderItem.orderItem
import com.lionTF.CShop.domain.shop.models.QOrders.orders
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class AdminOrderRepositoryCustomImpl(

    var queryFactory: JPAQueryFactory? = null

):AdminOrderRepositoryCustom {


    // 4개의 테이블을 조인하여 모든 주문 조회
    override fun findOrdersInfo(): List<GetAllOrdersDTO> {
        return queryFactory!!
            .select(Projections.constructor(
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
            ))
            .from(orders, member)
            .leftJoin(orderItem).on(orders.orderId.eq(orderItem.orders.orderId)).fetchJoin()
            .leftJoin(item).on(orderItem.item.itemId.eq(item.itemId)).fetchJoin()
            .where(member.memberId.eq(orders.member.memberId))
            .fetch()
    }

    // 4개의 테이블을 조인하여 회원의 ID로 주문 조회
    override fun findOrdersInfoByMemberId(keyword: String): List<GetAllOrdersDTO>? {

        val booleanBuilder: BooleanBuilder = BooleanBuilder()
        booleanBuilder.and(member.id.contains(keyword))

        return queryFactory!!
            .select(Projections.constructor(
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
            ))
            .from(orders, member)
            .leftJoin(orderItem).on(orders.orderId.eq(orderItem.orders.orderId)).fetchJoin()
            .leftJoin(item).on(orderItem.item.itemId.eq(item.itemId)).fetchJoin()
            .where(member.memberId.eq(orders.member.memberId).and(booleanBuilder))
            .fetch()
    }
}