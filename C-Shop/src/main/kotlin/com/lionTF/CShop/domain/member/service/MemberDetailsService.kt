package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.dto.AuthMemberDTO
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class MemberDetailsService(private val memberAuthRepository: MemberAuthRepository) : UserDetailsService {

    //id(username)을 기반으로 DB에 존재하는 회원 있는지 찾음
    override fun loadUserByUsername(username: String?): UserDetails?{
        val existMember: Optional<Member> = memberAuthRepository.findById(username!!)
        if(!existMember.isPresent){
            throw UsernameNotFoundException("아이디 비밀번호를 확인하세요.")
        }

        val member=existMember.get()

        return AuthMemberDTO.memberToAuthMemberDTO(member)
    }
}