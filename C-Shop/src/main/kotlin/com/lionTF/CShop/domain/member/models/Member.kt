package com.lionTF.CShop.domain.member.models

import com.lionTF.CShop.domain.member.dto.RequestSignUpDTO
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import javax.persistence.*

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
data class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null,

    @OneToMany(mappedBy = "member")
    private var orders: MutableList<Orders>? = null,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY,cascade = [CascadeType.ALL])
    private var cart: Cart? = null,

    var id: String,
    var password: String,
    var phoneNumber: String,
    var memberName: String,
    var address: String,
    private var memberStatus: Boolean=true,

    var role:MemberRole?=null
):BaseTimeEntity(){

    companion object{
        fun requestSignUpDTOToMember(requestSignUpDTO: RequestSignUpDTO):Member{
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

}