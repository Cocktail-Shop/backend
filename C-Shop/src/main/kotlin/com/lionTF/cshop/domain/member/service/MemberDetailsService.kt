package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(private val memberAuthRepository: MemberAuthRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails?{
        val existMember = memberAuthRepository.findById(username!!).orElseThrow{UsernameNotFoundException("아이디 비밀번호를 확인하세요.")}
        if(!existMember.memberStatus){
            throw UsernameNotFoundException("탈퇴한 회원입니다.")
        }
        return AuthMemberDTO.fromMember(existMember)
    }
}
