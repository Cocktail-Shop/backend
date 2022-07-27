package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailItemInfoDTO
import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class CocktailItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val cocktailItemId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    private var cocktail: Cocktail,
) {

    //칵테일 단건 조회시, cocktailItems에 들어갈 아이템 정보들을 dto로 변환해주는 메소드
    fun toReadCocktailItemDTO(): ReadCocktailItemDTO {
        val item = item.toReadCocktailItemInfoDTO()
        return ReadCocktailItemDTO(
            itemId = item.itemId,
            itemName = item.itemName,
            price = item.price,
            amount = item.amount,
        )
    }
}