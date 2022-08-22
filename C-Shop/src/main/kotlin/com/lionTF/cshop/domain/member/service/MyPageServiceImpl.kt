package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.*
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import com.lionTF.cshop.domain.member.service.memberinterface.MyPageService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class MyPageServiceImpl(
    private val memberAuthRepository: MemberAuthRepository,
    private val passwordEncoder: PasswordEncoder
) : MyPageService {

    override fun getMyPageInfo(memberId: Long): MyPageResponseDTO {
        return memberAuthRepository.findByMemberId(memberId)?.let { member ->
            MyPageResponseDTO.fromMember(member)
        } ?: throw NoSuchElementException("해당 회원 정보 찾을 수 없음")
    }

    @Transactional
    override fun updateMyPageInfo(memberId: Long, requestUpdateMyPageDTO: RequestUpdateMyPageDTO): MemberResponseDTO {
        val requestMember =
            memberAuthRepository.findByMemberId(memberId) ?: throw NoSuchElementException("해당 회원정보를 찾을 수 없음")

        val existInfo = memberAuthRepository.findById(requestUpdateMyPageDTO.id)
        val canUpdate =
            !(existInfo != null && existInfo.memberId != memberId)

        return if (canUpdate) {
            requestMember.updateMemberInfo(requestUpdateMyPageDTO)
            MemberResponseDTO.toSuccessMyPageUpdateResponseDTO()
        } else {
            MemberResponseDTO.toFailedMyPageUpdateResponseDTO()
        }
    }

    @Transactional
    override fun updatePassword(memberId: Long, passwordUpdateRequestDTO: PasswordUpdateRequestDTO): MemberResponseDTO {
        val existMember = memberAuthRepository.findByMemberId(memberId) ?: throw NoSuchElementException("해당 회원 정보 없음")

        val (pastPassword, newPassword) = passwordUpdateRequestDTO

        val isMatchCurrentPassword = passwordEncoder.matches(pastPassword, existMember.password)
        val isSamePastPassword = pastPassword == newPassword

        val canUpdate = isMatchCurrentPassword && !isSamePastPassword

        return if (canUpdate) {
            existMember.updatePassword(newPassword, passwordEncoder)
            MemberResponseDTO.toSuccessPasswordUpdateResponseDTO()
        } else {
            MemberResponseDTO.toFailedPasswordUpdateResponseDTO()
        }
    }

    @Transactional
    override fun deleteMember(authMemberDTO: AuthMemberDTO): MemberResponseDTO {
        return memberAuthRepository.findByMemberId(authMemberDTO.memberId)?.let { member ->
            member.deleteMember()
            MemberResponseDTO.toDeleteMemberResponseDTO()
        } ?: throw NoSuchElementException("해당 회원 정보 찾을 수 없음")
    }
}
