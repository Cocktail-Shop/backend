package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.QMember.*
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory

class AdminMemberRepositoryCustomImpl(

    var queryFactory: JPAQueryFactory? = null

) : AdminMemberRepositoryCustom {

    override fun findMembersInfo(keyword: String): List<Member>? {

        val booleanBuilder: BooleanBuilder = BooleanBuilder()
        booleanBuilder.and(member.id.contains(keyword))

        return queryFactory!!
            .selectFrom(member)
            .where(booleanBuilder)
            .fetch()

    }
}