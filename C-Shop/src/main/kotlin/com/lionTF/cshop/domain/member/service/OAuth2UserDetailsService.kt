package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


@Service
class OAuth2UserDetailsService(private val memberAuthRepository: MemberAuthRepository,
                               private val passwordEncoder: PasswordEncoder)
    : DefaultOAuth2UserService(){


    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val email=oAuth2User.attributes["email"] as String

        val existMember=memberAuthRepository.findById(email)

        return if(existMember.isPresent){
            AuthMemberDTO.fromMember(existMember.get())
        }else{
            val newMember=Member.fromOAuth2User(email)
            memberAuthRepository.save(newMember)
            AuthMemberDTO.fromMember(newMember)
        }

    }


}