package com.lionTF.CShop.domain.admin.models

import javax.persistence.*

@Entity
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
) {
}