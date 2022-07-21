package com.lionTF.CShop.domain.admin.models

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

}