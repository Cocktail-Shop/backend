package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.repository.AdminMemberRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminMemberServiceImpl(

    private val adminMemberRepository: AdminMemberRepository,

) : AdminMemberService {

    // 한명의 회원 삭제
    @Transactional
    override fun deleteOneMember(memberId: Long): AdminResponseDTO {
        val memberExisted = adminMemberRepository.existsById(memberId)

        return if (!memberExisted) {
            AdminResponseDTO.toFailDeleteMemberResponseDTO()
        } else {
            val member = adminMemberRepository.getReferenceById(memberId)
            member.deleteMember()

            AdminResponseDTO.toSuccessDeleteMemberResponseDTO()
        }
    }

    // 회원 ID로 회원 검색
    override fun findMembers(keyword: String, pageable: Pageable): MembersSearchDTO {
        val membersInfo = adminMemberRepository.findMembersInfo(keyword, pageable)

        return MembersSearchDTO.memberToResponseMemberSearchPageDTO(membersInfo, keyword)
    }

    // 회원 전체 조회
    override fun getAllMembers(pageable: Pageable): MembersSearchDTO {
        val members = adminMemberRepository.findAllByMemberStatus(pageable)

        return MembersSearchDTO.memberToResponseMemberSearchPageDTO(members)
    }

    // 존재하는 사용자인지 검사하는 함수
    private fun existedMember(memberId: Long): Member? {
        return adminMemberRepository.findMember(memberId)
    }

    // Form 으로부터 받아온 memberId 들이 존재하는지 검사
    private fun formToExistedMembers(memberIdList: MutableList<Long>): Boolean {
        return memberIdList.none { existedMember(it) == null }
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
