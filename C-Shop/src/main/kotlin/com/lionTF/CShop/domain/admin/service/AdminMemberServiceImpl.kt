package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminMemberRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.CShop.domain.member.models.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AdminMemberServiceImpl(

    private val adminMemberRepository: AdminMemberRepository,

) : AdminMemberService {

    // 한명의 회원 삭제
    override fun deleteOneMember(memberId: Long): AdminResponseDTO{
        val existsMember = adminMemberRepository.existsById(memberId)

        return if (!existsMember) {
            AdminResponseDTO.toFailDeleteMemberResponseDTO()
        } else {
            val member = adminMemberRepository.getReferenceById(memberId)
            member.deleteMember()

            AdminResponseDTO.toSuccessDeleteMemberResponseDTO()
        }
    }

    // 회원 ID로 회원 검색
    override fun findMembers(keyword: String, pageable: Pageable): ResponseSearchMembersResultDTO {
        val findMembersInfo = adminMemberRepository.findMembersInfo(keyword, pageable)

        return ResponseSearchMembersResultDTO.memberToResponseMemberSearchPageDTO(findMembersInfo, keyword)
    }

    // 회원 전체 조회
    override fun getAllMembers(pageable: Pageable): ResponseSearchMembersResultDTO {
        val findAllMember = adminMemberRepository.findAllByMemberStatus(pageable)

        return ResponseSearchMembersResultDTO.memberToResponseMemberSearchPageDTO(findAllMember, "")
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


    // 한명 이상의 회원 삭제
//    override fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO {
//
//        if (formToExistedMembers(deleteMembersDTO.memberIds)) {
//            for (memberId in deleteMembersDTO.memberIds) {
//                val member = adminMemberRepository.getReferenceById(memberId)
//
//                member.deleteMember()
//            }
//
//            return setDeleteSuccessMembersResultDTO()
//        } else {
//            return setDeleteFailMembersResultDTO()
//        }
//    }
}
