package com.lionTF.CShop.domain.member.models

import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.models.Orders
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val memberId: Long,

    @OneToMany(mappedBy = "member")
    private var orders: List<Orders>,

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private var cart: Cart,

    private var id: String,
    private var password: String,
    private var phoneNumber: String,
    private var memberName: String,
    private var address: String,
)