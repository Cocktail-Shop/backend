package com.lionTF.cshop.domain.admin.models

import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class CocktailItem(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cocktailItemId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    var cocktail: Cocktail

) : BaseTimeEntity() {

    companion object {
        fun requestCreateCocktailItemDTOtoCocktailItem(requestItem: Item, requestCocktail: Cocktail): CocktailItem {
            return CocktailItem(
                cocktail = requestCocktail,
                item = requestItem
            )
        }
    }
}
