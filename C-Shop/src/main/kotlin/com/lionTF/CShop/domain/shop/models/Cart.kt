package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.member.models.Member
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
    private val cartId: Long,

    @OneToMany(mappedBy = "cart")
    private var cartItem: List<CartItem>,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private var member: Member,
) {

}