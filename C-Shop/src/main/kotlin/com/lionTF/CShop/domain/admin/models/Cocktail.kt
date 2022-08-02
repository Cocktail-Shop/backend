package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.global.model.BaseTimeEntity
import lombok.*
import javax.persistence.*

@Entity
@EntityListeners
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Cocktail (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val cocktailId: Long,

    @OneToMany(mappedBy = "cocktail")
    var cocktailItem: List<CocktailItem>,
    var cocktailDescription: String,
    var cocktailName: String,
    var cocktailImgUrl: String,
    var cocktailStatus: Boolean,
) : BaseTimeEntity() {

}