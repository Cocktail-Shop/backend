package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteFailMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteSuccessMembersResultDTO
import com.lionTF.CShop.domain.admin.repository.AdminMemberRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.CShop.domain.member.models.Member
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AdminMemberServiceImpl(

    private val adminMemberRepository: AdminMemberRepository,

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

    // 존재하는 사용자인지 검사하는 함수
    override fun existedMember(memberId: Long): Optional<Member> {
        return adminMemberRepository.findById(memberId)
    }

    // Form으로부터 받아온 memberId들이 존재하는지 검사
    override fun formToExistedMembers(memberIdList: MutableList<Long>): Boolean {
        for (memberId in memberIdList) {
            when (existedMember(memberId).isEmpty) {
                true -> {return false}
            }
        }
        return true
    }
}