package com.lionTF.cshop.domain.member.controller.dto

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User


/*
 * UserDetails 인터페이스를 구현해놓은 User 클래스 상속 받은 클래스
 * loadByUsername 메서드에서 리턴값으로 사용하며 회원 정보를 담고 있음
 */
class AuthMemberDTO(
    username:String, password:String,
    authorities: MutableCollection<out GrantedAuthority>?,
    var memberId: Long?=null,
): User(username,password,authorities) {

    companion object{
        fun fromMember(member:Member) : AuthMemberDTO {
            return AuthMemberDTO(
                member.id,
                member.password,
                mutableSetOf(SimpleGrantedAuthority("ROLE_"+ member.role!!.name)),
                member.memberId
            )
        }

    }

}
