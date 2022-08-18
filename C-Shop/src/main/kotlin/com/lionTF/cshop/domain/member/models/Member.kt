package com.lionTF.cshop.domain.member.models

import com.lionTF.cshop.domain.member.controller.dto.RequestSignUpDTO
import com.lionTF.cshop.domain.member.controller.dto.RequestUpdateMyPageDTO
import com.lionTF.cshop.domain.shop.models.Cart
import com.lionTF.cshop.domain.shop.models.Orders
import com.lionTF.cshop.global.model.BaseTimeEntity
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*

@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long = 0,

    @OneToMany(mappedBy = "member")
    private var orders: MutableList<Orders>? = null,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY,cascade = [CascadeType.ALL])
    private var cart: Cart? = null,

    var id: String = "",
    var password: String = "",
    var phoneNumber: String = "",
    var memberName: String = "",
    var address: String = "",
    var email:String="",
    var detailAddress:String="",
    var memberStatus: Boolean = true,

    var role:MemberRole?=null,
    var fromSocial:Boolean=false
):BaseTimeEntity(){

    companion object{
        fun requestSignUpDTOToMember(requestSignUpDTO: RequestSignUpDTO):Member{
            return Member(
                id=requestSignUpDTO.id,
                password=requestSignUpDTO.password,
                phoneNumber=requestSignUpDTO.phoneNumber,
                memberName=requestSignUpDTO.memberName,
                address=requestSignUpDTO.address,
                email=requestSignUpDTO.email,
                detailAddress=requestSignUpDTO.detailAddress,
                role = MemberRole.MEMBER
            )
        }

        fun fromOAuth2User(email:String):Member{
            return Member(
                id=email,
                memberName=email,
                role = MemberRole.MEMBER,
                email=email,
                fromSocial = true
            )
        }
    }

    fun updateMember(requestUpdateMyPageDTO: RequestUpdateMyPageDTO){
        this.id=requestUpdateMyPageDTO.id
        this.address=requestUpdateMyPageDTO.address
        this.detailAddress=requestUpdateMyPageDTO.detailAddress
    }

    fun updateMemberPassword(newPassword:String,passwordEncoder:PasswordEncoder){
        this.password=passwordEncoder.encode(newPassword)
    }
    fun deleteMember(){
        memberStatus = false
    }

}