package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindMembersDTO
import com.lionTF.CShop.domain.member.models.Member
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import com.lionTF.CShop.domain.member.models.QMember.member
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Page
import org.springframework.data.support.PageableExecutionUtils


class AdminMemberRepositoryCustomImpl(

    var queryFactory: JPAQueryFactory? = null

) : AdminMemberRepositoryCustom {


    // 회원 ID로 회원 검색
    override fun findMembersInfo(keyword: String, pageable: Pageable): Page<FindMembersDTO> {
        val booleanBuilder = BooleanBuilder().and(member.id.contains(keyword))

        val content: List<FindMembersDTO> = queryFactory!!
            .select(
                Projections.constructor(
                    FindMembersDTO::class.java,
                    member.memberId,
                    member.id,
                    member.address,
                    member.memberName,
                    member.phoneNumber
                )
            )
            .from(member)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .where(
                booleanBuilder,
                member.memberStatus.eq(true)
            )
            .fetch()

        val countQuery: JPAQuery<Member> = queryFactory!!
            .selectFrom(member)
            .where(
                booleanBuilder,
                member.memberStatus.eq(true))

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    // 회원 전체 조회
    override fun findAllByMemberStatus(pageable: Pageable): Page<FindMembersDTO> {
        val content: List<FindMembersDTO> = queryFactory!!
            .select(
                Projections.constructor(
                    FindMembersDTO::class.java,
                    member.memberId,
                    member.id,
                    member.address,
                    member.memberName,
                    member.phoneNumber
                )
            )
            .from(member)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .where(
                member.memberStatus.eq(true),
            )
            .fetch()

        val countQuery: JPAQuery<Member> = queryFactory!!
            .selectFrom(member)
            .where(
                member.memberStatus.eq(true))

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }
}