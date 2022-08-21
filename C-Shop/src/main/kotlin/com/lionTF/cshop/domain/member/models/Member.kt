package com.lionTF.cshop.domain.member.models

import com.lionTF.cshop.domain.member.controller.dto.PreMemberInfoRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.SignUpRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.RequestUpdateMyPageDTO
import com.lionTF.cshop.global.model.BaseTimeEntity
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long = 0,

    var id: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    val memberName: String = "",
    var address: String = "",
    var email: String = "",
    var detailAddress: String = "",
    var memberStatus: Boolean = true,

    @Enumerated(EnumType.STRING)
    var role: MemberRole,
    var fromSocial: Boolean = false
) : BaseTimeEntity() {

    fun updateMemberInfo(requestUpdateMyPageDTO: RequestUpdateMyPageDTO) {
        this.id = requestUpdateMyPageDTO.id
        this.address = requestUpdateMyPageDTO.address
        this.detailAddress = requestUpdateMyPageDTO.detailAddress
    }

    fun updatePassword(newPassword: String, passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(newPassword)
    }

    fun deleteMember() {
        memberStatus = false
    }

    fun setPreMemberInfo(preMemberInfoRequestDTO: PreMemberInfoRequestDTO) {
        this.phoneNumber = preMemberInfoRequestDTO.phoneNumber
        this.address = preMemberInfoRequestDTO.address
        this.detailAddress = preMemberInfoRequestDTO.detailAddress
        this.role = MemberRole.MEMBER
    }

    companion object {
        fun fromRequestSignUpDTO(signUpRequestDTO: SignUpRequestDTO): Member {
            return Member(
                id = signUpRequestDTO.id,
                password = signUpRequestDTO.password,
                phoneNumber = signUpRequestDTO.phoneNumber,
                memberName = signUpRequestDTO.memberName,
                address = signUpRequestDTO.address,
                email = signUpRequestDTO.email,
                detailAddress = signUpRequestDTO.detailAddress,
                role = MemberRole.MEMBER
            )
        }

        fun fromOAuth2User(name: String, email: String): Member {
            return Member(
                id = email,
                memberName = name,
                role = MemberRole.PREMEMBER,
                email = email,
                fromSocial = true
            )
        }
    }
}
