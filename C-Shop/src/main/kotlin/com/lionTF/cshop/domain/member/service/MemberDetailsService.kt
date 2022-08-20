package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(private val memberAuthRepository: MemberAuthRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return memberAuthRepository.findById(username)?.let {member->
            when (member.memberStatus) {
                true -> AuthMemberDTO.fromMember(member)
                false -> throw UsernameNotFoundException("탈퇴한 회원")
            }
        } ?: throw UsernameNotFoundException("아이디 비밀번호를 확인")
    }
}
