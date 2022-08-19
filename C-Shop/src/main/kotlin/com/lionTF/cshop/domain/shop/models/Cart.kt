package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cartId: Long?=null,

    @OneToMany(mappedBy = "cart")
    var cartItem: List<CartItem>?=null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member?=null,
) : BaseTimeEntity() {
    // 장바구니 상품 삭제
    fun deleteCartItem(itemId: Long) {
        // TODO : cartItem list에서 삭제
    }
}

