package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminCocktailServiceTest (

    @Autowired
    private val adminCocktailService: AdminCocktailService,

    @Autowired
    private val adminItemRepository: AdminItemRepository,

){

    @BeforeEach
    fun initDB(){
        var createItemDTO1 = CreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 2,
            degree = 10,
            itemDescription = "test"
        )

        var item1 = adminItemRepository.save(itemToItemDTO(createItemDTO1))

        var createItemDTO2 = CreateItemDTO(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 1,
            amount = 2,
            degree = 10,
            itemDescription = "test"
        )

        var item2 = adminItemRepository.save(itemToItemDTO(createItemDTO2))

        var createItemDTO3 = CreateItemDTO(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 1,
            amount = 2,
            degree = 10,
            itemDescription = "test"
        )

        var item3 = adminItemRepository.save(itemToItemDTO(createItemDTO3))
    }

    @Test
    @DisplayName("칵테일 생성 test")
    fun createCocktailTest() {
        //given

        var itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(1L)
        itemIdList.add(2L)
        itemIdList.add(3L)

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktail = adminCocktailService.createCocktail(createCocktailDTO)

        //then
        assertThat(cocktail.status).isEqualTo(setCreateSuccessCocktailResultDTO().status)
        assertThat(cocktail.message).isEqualTo(setCreateSuccessCocktailResultDTO().message)
    }

    @Test
    @DisplayName("칵테일 생성시 존재하지 않는 상품 예외 test")
    fun formToExistedItemsTest() {
        //given

        var itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(1L)
        itemIdList.add(2L)
        itemIdList.add(310L)

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktail = adminCocktailService.createCocktail(createCocktailDTO)

        //then
        assertThat(cocktail.status).isEqualTo(FailToNoContentItemResultDTO().status)
        assertThat(cocktail.message).isEqualTo(FailToNoContentItemResultDTO().message)
        println("FailToNoContentItemResultDTO().message = ${FailToNoContentItemResultDTO().message}")
    }

    @Test
    @DisplayName("이미 등록된 칵테일인지 검사하는 test")
    fun existedCocktailTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(1L)
        itemIdList.add(2L)
        itemIdList.add(3L)

        var createCocktailDTO1 = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        var createCocktailDTO2 = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        adminCocktailService.createCocktail(createCocktailDTO1)

        //when
        val duplicatedCocktail = adminCocktailService.createCocktail(createCocktailDTO2)

        //then
        assertThat(duplicatedCocktail.status).isEqualTo(setCreateFailCocktailResultDTO().status)
        assertThat(duplicatedCocktail.message).isEqualTo(setCreateFailCocktailResultDTO().message)
        println("setCreateFailCocktailResultDTO().message = ${setCreateFailCocktailResultDTO().message}")
    }
}