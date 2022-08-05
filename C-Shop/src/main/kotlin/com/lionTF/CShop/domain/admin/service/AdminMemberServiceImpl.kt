package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminMemberRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.QMember
import com.lionTF.CShop.domain.member.models.QMember.*
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import javax.transaction.Transactional

@Service
@Transactional
class AdminMemberServiceImpl(

    private val adminMemberRepository: AdminMemberRepository,
    private val queryFactory: JPAQueryFactory,

    ) : AdminMemberService {

    // 회원 삭제
    override fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO {

        if (formToExistedMembers(deleteMembersDTO.memberIds)) {
            for (memberId in deleteMembersDTO.memberIds) {
                val member = adminMemberRepository.getReferenceById(memberId)

                member.deleteMember()
            }

            return setDeleteSuccessMembersResultDTO()
        } else {
            return setDeleteFailMembersResultDTO()
        }
    }

    // 회원 ID로 회원 검색
    override fun findMembers(keyword: String): List<FindMembersDTO> {

        val findMembersInfo = adminMemberRepository.findMembersInfo(keyword)

        return findMembersInfo!!.stream()
            .map<FindMembersDTO>(Function<Member, FindMembersDTO> { m: Member ->
                FindMembersDTO(
                    m.id,
                    m.address,
                    m.memberName,
                    m.phoneNumber,
                )
            }).collect(Collectors.toList())
    }

    // 존재하는 사용자인지 검사하는 함수
    private fun existedMember(memberId: Long): Optional<Member> {
        return adminMemberRepository.findById(memberId)
    }

    // Form으로부터 받아온 memberId들이 존재하는지 검사
    private fun formToExistedMembers(memberIdList: MutableList<Long>): Boolean {
        for (memberId in memberIdList) {
            when (existedMember(memberId).isEmpty) {
                true -> {return false}
            }
        }
        return true
    }
}