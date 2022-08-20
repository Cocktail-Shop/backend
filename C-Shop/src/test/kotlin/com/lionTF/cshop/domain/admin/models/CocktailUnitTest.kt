package com.lionTF.cshop.domain.admin.models

import com.lionTF.cshop.domain.admin.controller.dto.CocktailCreateRequestDTO
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Rollback

@Rollback
internal class CocktailUnitTest {

    private var cocktail = Cocktail(
        cocktailId = 1L,
        cocktailDescription = "test",
        cocktailName = "cocktail",
        cocktailStatus = true,
        category = Category.COCKTAIL
    )

    @Test
    @DisplayName("칵테일 삭제 test")
    fun deleteCocktailTest() {
        //when
        cocktail.deleteCocktail()

        //then
        assertThat(cocktail.cocktailStatus).isEqualTo(false)
    }

    @Test
    @DisplayName("칵테일 상품 수정 test")
    fun updateCocktailTest() {
        //given
        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktailName-update",
            cocktailDescription = "cocktailDescription-update",
            category = Category.COCKTAIL
        )

        //when
        cocktail.updateCocktail(requestCreateCocktailDTO, "test")

        //then
        assertThat(cocktail.cocktailName).isEqualTo(requestCreateCocktailDTO.cocktailName)
        assertThat(cocktail.cocktailDescription).isEqualTo(requestCreateCocktailDTO.cocktailDescription)
    }
}
