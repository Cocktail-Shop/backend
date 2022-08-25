package com.lionTF.cshop.domain.shop.models

import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class CocktailWishList(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cocktailWishListId: Long = 0,

    val memberId: Long? = 0,
    val cocktailId: Long? = 0,

    val cocktailName: String = "",
    val cocktailImgUrl: String = "",
) : BaseTimeEntity() {

    companion object {
        fun toEntity(cocktail: Cocktail, memberId: Long): CocktailWishList {
            return CocktailWishList(
                memberId = memberId,
                cocktailId = cocktail.cocktailId,
                cocktailName = cocktail.cocktailName,
                cocktailImgUrl = cocktail.cocktailImgUrl
            )
        }
    }
}
