package com.lionTF.CShop.domain.member.dto

import com.lionTF.CShop.domain.member.models.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors
import javax.persistence.*


class AuthMemberDTO(
    username:String, password:String,
    authorities: MutableCollection<out GrantedAuthority>?,
    var memberId: Long?=null,
): User(username,password,authorities) {//UserDetails 인터페이스를 구현해놓은 User 클래스 상속

    companion object{
        fun memberToAuthMemberDTO(member:Member) : AuthMemberDTO {
            return AuthMemberDTO(
                member.id,
                member.password,
                mutableSetOf(SimpleGrantedAuthority("ROLE_"+ member.role!!.name)),
                member.memberId
            )
        }
    }

}
