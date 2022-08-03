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

    override fun loadUserByUsername(username: String?): UserDetails?{
        val result: Optional<Member> = memberAuthRepository.findById(username!!)
        if(!result.isPresent){
            throw UsernameNotFoundException("아이디 비밀번호를 확인하세요.")
        }

        val member=result.get()

        return AuthMemberDTO.memberToAuthMemberDTO(member)
    }
}