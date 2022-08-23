package com.lionTF.cshop.domain.admin.models

import com.lionTF.cshop.domain.admin.controller.dto.CocktailCreateRequestDTO
import javax.persistence.*

import com.lionTF.cshop.global.model.BaseTimeEntity

@Entity
@EntityListeners
class Cocktail(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cocktailId: Long = 0,

    @OneToMany(mappedBy = "cocktail")
    val cocktailItem: MutableList<CocktailItem>? = null,

    var cocktailDescription: String = "",
    var cocktailName: String = "",
    var cocktailImgUrl: String = "",
    var cocktailStatus: Boolean = true,

    @Enumerated(EnumType.STRING)
    var category: Category,
) : BaseTimeEntity() {

    // 칵테일 상품 삭제
    fun deleteCocktail() {
        cocktailStatus = false
    }

    // 칵테일 상품 수정
    fun updateCocktail(requestCreateCocktailDTO: CocktailCreateRequestDTO, cocktailImgUrl: String?) {
        cocktailName = requestCreateCocktailDTO.cocktailName
        cocktailDescription = requestCreateCocktailDTO.cocktailDescription
        category = Category.COCKTAIL
        this.cocktailImgUrl = cocktailImgUrl!!
    }

    companion object {
        fun toEntity(
            requestCreateCocktailDTO: CocktailCreateRequestDTO,
            cocktailImgUrl: String?
        ): Cocktail {
            return Cocktail(
                cocktailDescription = requestCreateCocktailDTO.cocktailDescription,
                cocktailName = requestCreateCocktailDTO.cocktailName,
                category = requestCreateCocktailDTO.category,
                cocktailImgUrl = cocktailImgUrl!!
            )
        }
    }
}
