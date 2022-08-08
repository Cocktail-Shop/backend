package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.admin.controller.dto.CreateCocktailDTO
import javax.persistence.*

import com.lionTF.CShop.global.model.BaseTimeEntity

@Entity
@EntityListeners
class Cocktail (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cocktailId: Long = 0,

    @OneToMany(mappedBy = "cocktail")
    var cocktailItem: MutableList<CocktailItem>? = null,

    var cocktailDescription: String = "",
    var cocktailName: String = "",
    var cocktailImgUrl: String = "",
    var cocktailStatus: Boolean = true,

    @Enumerated(EnumType.STRING)
    var category: Category,
) : BaseTimeEntity() {

    // 칵테일 상품 삭제
    fun deleteCocktail(){
        cocktailStatus = false
    }

    // 칵테일 상품 수정
    fun updateCocktail(createCocktailDTO: CreateCocktailDTO) {
        cocktailName = createCocktailDTO.cocktailName
        cocktailDescription = cocktailDescription
    }
}