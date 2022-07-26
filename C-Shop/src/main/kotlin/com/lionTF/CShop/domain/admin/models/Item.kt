package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.OrderItem
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val itemId: Long,

    @OneToMany(mappedBy = "item")
    private var cocktailItem: List<CocktailItem>,

    @OneToMany(mappedBy = "item")
    private var orderItem: List<OrderItem>,

    @OneToMany(mappedBy = "item")
    private var cartItem: List<CartItem>,

    private val price: Int,
    private val amount: Int,
    private val degree: Int,
    private var itemDescription: String,
    private var itemImgUrl: String,

    @Enumerated(EnumType.STRING)
    private var category: Category,
    private var itemStatus: Boolean,
) {
    fun toReadItemDTO(): ReadItemDTO {
            return ReadItemDTO(
                itemId = itemId,
                price = price,
                amount = amount,
                degree = degree,
                itemDescription = itemDescription,
                itemImgUrl = itemImgUrl,
                category = category,
                itemStatus = itemStatus,
            )
    }
}
