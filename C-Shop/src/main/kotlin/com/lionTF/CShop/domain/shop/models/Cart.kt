package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
