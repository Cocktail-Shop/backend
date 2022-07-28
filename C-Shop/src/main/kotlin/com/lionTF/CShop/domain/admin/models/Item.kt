package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailItemInfoDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadItemResultDTO
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.OrderItem
import com.lionTF.CShop.global.HttpStatus
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

    private var itemName: String,
    private var price: Int,
    private var amount: Int,
    private var degree: Int,
    private var itemDescription: String,
    private var itemImgUrl: String,

    @Enumerated(EnumType.STRING)
    private var category: Category,
    private var itemStatus: Boolean,
) {
    //아이템에 관한 정보를 dto로 변환하는 메소드
    fun toReadItemDTO(): ReadItemDTO {
            return ReadItemDTO(
                itemId = itemId,
                category = category,
                itemName = itemName,
                price = price,
                totalAmount = amount,
                degree = degree,
                itemDescription = itemDescription,
                itemImgUrl = itemImgUrl,
                itemStatus = itemStatus,
            )
    }

    //상품 단건 조회시, 응답 형태를 맞춰주기 위한 메소드
    fun toReadItemResultDTO(result: ReadItemDTO): ReadItemResultDTO {
        return ReadItemResultDTO(
            status = HttpStatus.OK,
            message = "상품 조회 성공",
            result = result
        )
    }

    //CocktailItem entity에서 toReadCocktailItemDTO()를 수행시켜주기 위한 메소드
    fun toReadCocktailItemInfoDTO(): ReadCocktailItemInfoDTO {
        return ReadCocktailItemInfoDTO(
            itemId = itemId,
            itemName = itemName,
            price = price,
            amount = amount,
        )
    }
}
