package com.lionTF.CShop.domain.member.security

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.context.support.WithSecurityContextFactory


class WithMockCustomUserSecurityContextFactory : WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    lateinit var memberAuthRepository: MemberAuthRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    override fun createSecurityContext(customUser: WithMockCustomUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val member: Member = Member(
            id = "test",
            password= passwordEncoder.encode("test123"),
            phoneNumber = "01012341234",
            memberName = "사용자",
            email = "test@naver.com",
            address = "서울시 동작구 상도동",
            detailAddress = "XX빌딩 103호"
        )
        member.role= MemberRole.MEMBER
        memberAuthRepository.save(member)

        val principal =AuthMemberDTO.memberToAuthMemberDTO(member)
        val auth: Authentication =
            UsernamePasswordAuthenticationToken(principal, principal.password, principal.authorities)

        context.authentication = auth
        return context
    }
}