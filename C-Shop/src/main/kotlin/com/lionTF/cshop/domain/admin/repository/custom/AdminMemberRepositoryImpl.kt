package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.FindMembersDTO
import com.lionTF.cshop.domain.member.models.Member
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Pageable
import com.lionTF.cshop.domain.member.models.QMember.member
import com.querydsl.jpa.impl.JPAQuery
import org.springframework.data.domain.Page
import org.springframework.data.support.PageableExecutionUtils


class AdminMemberRepositoryImpl(

    private val queryFactory: JPAQueryFactory? = null

) : AdminMemberRepositoryCustom {

    // 회원 전체 조회
    override fun findAllByMemberStatus(pageable: Pageable): Page<FindMembersDTO> {
        // 데이터 내용을 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val content: List<FindMembersDTO> = contentInquire(pageable, null)

        // 카운트를 별도로 조회하는 로직입니다.
        // TODO 회원 ID로 회원 검색하는 로직과 비슷하여 함수로 추출하고 전체 조회이기 떄문에 booleanBuilder를 null로 처리하였는데 이것이 옳은가에 대한 고민입니다.
        val countQuery: JPAQuery<Member> = countInquire(null)

        // 위에서 반환된 데이터 내용과 카운트를 반환합니다.
        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 회원 ID로 회원 검색
    override fun findMembersInfo(keyword: String, pageable: Pageable): Page<FindMembersDTO> {
        val booleanBuilder = booleanBuilder(keyword)

        val content: List<FindMembersDTO> = contentInquire(pageable, booleanBuilder)
        val countQuery: JPAQuery<Member> = countInquire(booleanBuilder)

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount)
    }

    // 데이터 내용을 조회하는 함수입니다.
    private fun contentInquire(
        pageable: Pageable,
        booleanBuilder: BooleanBuilder?
    ): List<FindMembersDTO> {
        return queryFactory!!
            .select(
                Projections.constructor(
                    FindMembersDTO::class.java,
                    member.memberId,
                    member.id,
                    member.address,
                    member.memberName,
                    member.phoneNumber,
                    member.createdAt
                )
            )
            .from(member)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .where(
                booleanBuilder,
                isEqualMemberStatus()
            )
            .fetch()
    }

    // 카운트를 별도로 조회하는 함수입니다.
    private fun countInquire(booleanBuilder: BooleanBuilder?): JPAQuery<Member> {
        return queryFactory!!
            .selectFrom(member)
            .where(
                booleanBuilder,
                isEqualMemberStatus()
            )
    }

    // BooleanBuilder를 생성하는 함수입니다.
    private fun booleanBuilder(keyword: String): BooleanBuilder? {
        return BooleanBuilder().and(member.id.contains(keyword))
    }

    private fun isEqualMemberStatus() = member.memberStatus.eq(true)
}