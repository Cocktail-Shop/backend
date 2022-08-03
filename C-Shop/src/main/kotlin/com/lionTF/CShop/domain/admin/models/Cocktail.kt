package com.lionTF.CShop.domain.admin.models

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
) : BaseTimeEntity() {

    fun deleteCocktail(){
        cocktailStatus = false
    }
}