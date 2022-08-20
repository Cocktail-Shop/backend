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

    override fun getMyPageInfo(memberId: Long): ResponseMyPageDTO {
        val member = memberAuthRepository.findByMemberId(memberId).orElseThrow()
        return ResponseMyPageDTO.fromMember(member)
    }

    @Transactional
    override fun updateMyPageInfo(memberId: Long, requestUpdateMyPageDTO: RequestUpdateMyPageDTO): ResponseDTO {
        val existMember = memberAuthRepository.findByMemberId(memberId).orElseThrow()

        val existInfo = memberAuthRepository.findById(requestUpdateMyPageDTO.id)
        val canUpdate =
            !(existInfo.isPresent && existInfo.get().memberId != memberId)

        return if (canUpdate) {
            existMember.updateMemberInfo(requestUpdateMyPageDTO)
            ResponseDTO.toSuccessUpdateMyPageResponseDTO()
        } else {
            ResponseDTO.toFailedUpdateMyPageResponseDTO()
        }

    }

    @Transactional
    override fun updatePassword(memberId: Long, requestUpdatePasswordDTO: RequestUpdatePasswordDTO): ResponseDTO {
        val existMember = memberAuthRepository.findByMemberId(memberId).orElseThrow()

        val pastPassword = requestUpdatePasswordDTO.pastPassword
        val newPassword = requestUpdatePasswordDTO.newPassword

        val isMatchExistPassword = passwordEncoder.matches(pastPassword, existMember.password)
        val isPastSameNewPassword = pastPassword == newPassword

        val canUpdate = isMatchExistPassword && !isPastSameNewPassword

        return if (canUpdate) {
            existMember.updatePassword(pastPassword, passwordEncoder)
            ResponseDTO.toSuccessUpdatePasswordDTO()
        } else {
            ResponseDTO.toFailedUpdatePasswordDTO()
        }
    }

    @Transactional
    override fun deleteMember(authMemberDTO: AuthMemberDTO): ResponseDTO {
        val existMember = memberAuthRepository.findByMemberId(authMemberDTO?.memberId).orElseThrow()
        existMember.deleteMember()
        memberAuthRepository.save(existMember)
        return ResponseDTO.toDeleteMemberResponseDTO()
    }
}
