package com.lionTF.CShop.domain.member.models

import com.lionTF.CShop.domain.member.dto.SignUpDTO
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import javax.persistence.*

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    var memberStatus: Boolean = true,

    var role:MemberRole?=null
):BaseTimeEntity(){

    companion object{
        fun requestSignUpDTOToMember(requestSignUpDTO: SignUpDTO.RequestDTO):Member{
            return Member(
                id=requestSignUpDTO.id,
                password=requestSignUpDTO.password,
                phoneNumber=requestSignUpDTO.phoneNumber,
                memberName=requestSignUpDTO.memberName,
                address=requestSignUpDTO.address,
                cart=Cart()
            )
        }
    }

    fun deleteMember(){
        memberStatus = false
    }

}