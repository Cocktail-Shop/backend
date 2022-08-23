package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.repository.AdminMemberRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminMemberService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminMemberServiceImpl(
    private val adminMemberRepository: AdminMemberRepository,
) : AdminMemberService {

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

    override fun findMembers(keyword: String, pageable: Pageable): MembersSearchDTO {
        val membersInfo = adminMemberRepository.findMembersInfo(keyword, pageable)

        return MembersSearchDTO.toFormDTO(membersInfo, keyword)
    }

    override fun getAllMembers(pageable: Pageable): MembersSearchDTO {
        val members = adminMemberRepository.findAllByMemberStatus(pageable)

        return MembersSearchDTO.toFormDTO(members)
    }
}
