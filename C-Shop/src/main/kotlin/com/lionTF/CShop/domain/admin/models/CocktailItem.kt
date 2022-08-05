package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class CocktailItem (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cocktailItemId: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    var item: Item,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cocktail_id")
    var cocktail: Cocktail,
) : BaseTimeEntity() {}
