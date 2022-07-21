package com.lionTF.CShop.domain.shop.models

import com.lionTF.CShop.domain.member.models.Member
import lombok.*
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Orders(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val orderId: Long,

    @OneToMany(mappedBy = "orders")
    private var orderItem: List<OrderItem>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private var member: Member,

    private var orderDate: LocalDateTime,
    private var orderAddress: String,
){

}