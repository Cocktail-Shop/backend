package com.lionTF.CShop.domain.admin.models

import com.fasterxml.jackson.databind.BeanDescription
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadCocktailResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
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
    private val cocktailId: Long,

    @OneToMany(mappedBy = "cocktail")
    private var cocktailItem: List<CocktailItem>,
    private var cocktailDescription: String,
    private var cocktailName: String,
    private var cocktailImgUrl: String,
    private var cocktailStatus: Boolean,
){

    //칵테일 단건 조회 응답 최종 형태 dto를 만들어주는 메소드
    fun toReadCocktailResultDTO(result: ReadCocktailDTO): ReadCocktailResultDTO {
        return ReadCocktailResultDTO(
            status = HttpStatus.OK,
            message = "칵테일 단건 조회 성공",
            result = result
        )
    }

    //칵테일 단건 조회 result부분에 들어갈 정보들을 dto로 변환
    fun toReadCocktailDTO(): ReadCocktailDTO {
        return ReadCocktailDTO(
            cocktailId = cocktailId,
            cocktailName = cocktailName,
            cocktailDescription = cocktailDescription,
            cocktailImgUrl = cocktailImgUrl,
            cocktailStatus = cocktailStatus,
            cocktailItems = cocktailItem.map{it.toReadCocktailItemDTO()}

        )
    }
}