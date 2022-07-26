package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartId: Long? = null,

    @OneToMany(mappedBy = "cart")
    var cartItem: List<CartItem>? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member? = null,
) : BaseTimeEntity() {

    companion object {
        fun fromMember(member: Member): Cart {
            return Cart(member = member)
        }
    }
}
