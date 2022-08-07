package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersDTO
import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.member.models.QMember.member
import com.lionTF.CShop.domain.shop.models.QOrderItem.orderItem
import com.lionTF.CShop.domain.shop.models.QOrders.orders
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class AdminOrderRepositoryCustomImpl(

    var queryFactory: JPAQueryFactory? = null

):AdminOrderRepositoryCustom {

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
}