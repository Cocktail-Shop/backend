package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.CShop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import javax.transaction.Transactional

@SpringBootTest
@Transactional
@Rollback
internal class AdminCocktailServiceTest {

    @Autowired
    private lateinit var adminCocktailService: AdminCocktailService

    @Autowired
    private lateinit var adminItemRepository: AdminItemRepository

    @Autowired
    private lateinit var adminCocktailRepository: AdminCocktailRepository

    @Autowired
    private lateinit var adminCocktailItemRepository: AdminCocktailItemRepository

    private var item1: Item? = null
    private var item2: Item? = null
    private var item3: Item? = null
    private var cocktail: CreateCocktailResultDTO? = null
    private var cocktailItem: List<CocktailItem>? = null

    @BeforeEach
    fun init(){
        var createItemDTO1 = ItemDTO(
            itemName = "test1",

            category = Category.ALCOHOL,
            price = 1,
            amount = 2,
            degree = 10,
            itemDescription = "test"
        )

        item1 = adminItemRepository.save(itemDTOToItem(createItemDTO1))

        var createItemDTO2 = ItemDTO(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 1,
            amount = 2,
            degree = 10,
            itemDescription = "test"
        )

        item2 = adminItemRepository.save(itemDTOToItem(createItemDTO2))

        var createItemDTO3 = ItemDTO(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 1,
            amount = 2,
            degree = 10,
            itemDescription = "test"
        )

        item3 = adminItemRepository.save(itemDTOToItem(createItemDTO3))


        // 칵테일 생성
        var itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        item3?.let { itemIdList.add(it.itemId) }

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        cocktail = adminCocktailService.createCocktail(createCocktailDTO)

        val cocktailId = cocktail?.let { adminCocktailRepository.getReferenceById(it.cocktailId) }

        // 칵테일 아이템 생성
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        item1?.let {
            cocktailId?.let {
                    it1 -> cocktailItemToCocktailItemDTO(it, it1)
            }
        }?.let { cocktailItemList.add(it) }

        item2?.let {
            cocktailId?.let {
                    it1 -> cocktailItemToCocktailItemDTO(it, it1)
            }
        }?.let { cocktailItemList.add(it) }

        cocktailItem = adminCocktailItemRepository.saveAll(cocktailItemList)
    }

    @Test
    @DisplayName("칵테일 생성 test")
    fun createCocktailTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        item3?.let { itemIdList.add(it.itemId) }

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail2",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktailTest = adminCocktailService.createCocktail(createCocktailDTO)

        //then
        assertThat(cocktailTest.status).isEqualTo(setCreateSuccessCocktailResultDTO(cocktailTest.cocktailId).status)
        assertThat(cocktailTest.message).isEqualTo(setCreateSuccessCocktailResultDTO(cocktailTest.cocktailId).message)
    }

    @Test
    @DisplayName("칵테일 생성시 존재하지 않는 상품 예외 test")
    fun formToExistedItemsTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        itemIdList.add(310L)

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail10",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktailException = adminCocktailService.createCocktail(createCocktailDTO)

        //then
        assertThat(cocktailException.status).isEqualTo(failToNoContentItemResultDTO().status)
        assertThat(cocktailException.message).isEqualTo(failToNoContentItemResultDTO().message)
    }

    @Test
    @DisplayName("이미 등록된 칵테일인지 검사하는 test")
    fun existedCocktailTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        item3?.let { itemIdList.add(it.itemId) }

        var createCocktailDTO1 = CreateCocktailDTO(
            cocktailName = "cocktail19",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        var createCocktailDTO2 = CreateCocktailDTO(
            cocktailName = "cocktail19",
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


    @Test
    @DisplayName("칵테일 상품 삭제 test")
    fun deleteItemsTest() {
        //given
        val cocktailItemList: MutableList<Long> = mutableListOf()

        cocktailItemList.add(cocktail!!.cocktailId)

        val deleteCocktailDTO =  DeleteCocktailDTO(
            cocktailItemList
        )

        //when
        val deleteCocktail = adminCocktailService!!.deleteCocktail(deleteCocktailDTO)

        println("deleteCocktail = ${deleteCocktail.message}")

        //then
        assertThat(deleteCocktail.status).isEqualTo(setDeleteSuccessCocktailResultDTO().status)
        assertThat(deleteCocktail.message).isEqualTo(setDeleteSuccessCocktailResultDTO().message)

        val referenceById = cocktail?.let { adminCocktailRepository!!.getReferenceById(it.cocktailId) }

        referenceById?.let { assertThat(it.cocktailStatus).isEqualTo(false) }
    }

    @Test
    @DisplayName("존재하지 않는 칵테일을 삭제할 예외 test")
    fun deleteItemsExceptionTest() {
        //given
        val cocktailItemList: MutableList<Long> = mutableListOf()

        cocktail?.let { cocktailItemList.add(it.cocktailId) }
        cocktailItemList.add(1231L)

        val deleteCocktailDTO =  DeleteCocktailDTO(
            cocktailItemList
        )

        //when
        val deleteCocktail = adminCocktailService!!.deleteCocktail(deleteCocktailDTO)

        //then
        assertThat(deleteCocktail.status).isEqualTo(setDeleteFailCocktailResultDTO().status)
        assertThat(deleteCocktail.message).isEqualTo(setDeleteFailCocktailResultDTO().message)

        val referenceById = cocktail?.let { adminCocktailRepository!!.getReferenceById(it.cocktailId) }

        referenceById?.let { assertThat(it.cocktailStatus).isEqualTo(true) }
    }


    @Test
    @DisplayName("칵테일 상품 수정 test")
    fun updateCocktailTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        item3?.let { itemIdList.add(it.itemId) }

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val updateCocktail = cocktail?.let { adminCocktailService.updateCocktail(createCocktailDTO, it.cocktailId) }

        val countCocktailItem = adminCocktailItemRepository.count()

        //then
        updateCocktail?.let { assertThat(it.status).isEqualTo(setUpdateSuccessCocktailResultDTO(updateCocktail.cocktailId).status) }
        assertThat(updateCocktail?.message).isEqualTo(updateCocktail?.let { setUpdateSuccessCocktailResultDTO(it.cocktailId).message })
        assertThat(countCocktailItem).isEqualTo(1)
    }

    @Test
    @DisplayName("칵테일 상품 수정시 존재하지 않는 칵테일 수정시 예외 test")
    fun updateCocktailNoContentCocktailExceptionTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(100L)

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val updateCocktail = adminCocktailService.updateCocktail(createCocktailDTO, 10L)

        //then
        assertThat(updateCocktail.status).isEqualTo(setUpdateFailCocktailResultDTO().status)
        assertThat(updateCocktail.message).isEqualTo(setUpdateFailCocktailResultDTO().message)
    }

    @Test
    @DisplayName("칵테일 상품 수정시 존재하지 않는 item으로 발생하는 예외 test")
    fun updateCocktailNoContentItemExceptionTest() {
        //given
        var itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(100L)

        var createCocktailDTO = CreateCocktailDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val updateCocktail = cocktail?.let { adminCocktailService.updateCocktail(createCocktailDTO, it.cocktailId) }

        //then
        assertThat(updateCocktail?.status).isEqualTo(failToNoContentItemResultDTO().status)
        assertThat(updateCocktail?.message).isEqualTo(failToNoContentItemResultDTO().message)
    }
}