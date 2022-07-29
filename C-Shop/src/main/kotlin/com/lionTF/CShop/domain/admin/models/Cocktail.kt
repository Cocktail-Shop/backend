package com.lionTF.CShop.domain.admin.models

import com.fasterxml.jackson.databind.BeanDescription
import com.lionTF.CShop.domain.shop.controller.dto.CocktailDTO
import com.lionTF.CShop.domain.shop.controller.dto.CocktailItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.CocktailResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.ItemDTO
import com.lionTF.CShop.global.HttpStatus
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
    val cocktailId: Long,

    @OneToMany(mappedBy = "cocktail")
    var cocktailItem: List<CocktailItem>,
    var cocktailDescription: String,
    var cocktailName: String,
    var cocktailImgUrl: String,
    var cocktailStatus: Boolean,
)