package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.shop.controller.dto.CocktailItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.CocktailItemInfoDTO
import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import javax.persistence.*

@Entity
@EntityListeners
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class CocktailItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cocktailItemId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    var cocktail: Cocktail,
) : BaseTimeEntity() {}
