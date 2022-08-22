package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import com.lionTF.cshop.domain.shop.models.Cart
import com.lionTF.cshop.domain.shop.repository.CartRepository
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class OAuth2UserDetailsService(
    private val memberAuthRepository: MemberAuthRepository,
    private val cartRepository: CartRepository
) : DefaultOAuth2UserService() {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)

        val account = oAuth2User.attributes["kakao_account"] as Map<*, *>
        val properties = oAuth2User.attributes["properties"] as Map<*, *>

        val email = account["email"] as String? ?: throw UsernameNotFoundException("이메일 동의 필요")
        val name = properties["nickname"] as String

        val existMember = memberAuthRepository.findById(email)

        return if (existMember != null) {
            when (existMember.memberStatus) {
                true -> AuthMemberDTO.fromMember(existMember)
                false -> throw UsernameNotFoundException("탈퇴한 회원")
            }
        } else {
            val newMember = Member.fromOAuth2User(name, email)
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart.fromMember(newMember))
            AuthMemberDTO.fromMember(newMember)
        }
    }
}
