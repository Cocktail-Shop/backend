package com.lionTF.CShop.domain.admin.models

import lombok.*
import javax.persistence.*

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Cocktail (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val cocktailId: Long,

    @OneToMany(mappedBy = "cocktail")
    private var cocktailItem: List<CocktailItem>,

    private var cocktailName: String,
    private var cocktailImgUrl: String,
    private var cocktailStatus: Boolean,
)