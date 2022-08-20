package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.models.CocktailItem
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.cshop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminCocktailService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
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
    private var cocktail: Cocktail? = null
    private var cocktailItem: List<CocktailItem>? = null

    @BeforeEach
    fun init(){
        val itemDTO1 = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item1 = adminItemRepository.save(Item.requestCreateItemDTOtoItem(itemDTO1, "test"))

        val itemDTO2 = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item2 = adminItemRepository.save(Item.requestCreateItemDTOtoItem(itemDTO2, "test"))

        val itemDTO3 = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item3 = adminItemRepository.save(Item.requestCreateItemDTOtoItem(itemDTO3, "test"))


        // 칵테일 생성
        var itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }

        var requestCreateCocktailDTO1 = CocktailCreateRequestDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        cocktail = adminCocktailRepository.save(Cocktail.requestCreateCocktailDTOtoCocktail(requestCreateCocktailDTO1, "test"))


        // 칵테일 아이템 생성
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        val cocktailItem1 = CocktailItem(
            cocktail = cocktail!!,
            item = item1!!,
        )

        val cocktailItem2 = CocktailItem(
            cocktail = cocktail!!,
            item = item2!!,
        )

        val cocktailItem3 = CocktailItem(
            cocktail = cocktail!!,
            item = item3!!,
        )

        cocktailItemList.add(cocktailItem1)
        cocktailItemList.add(cocktailItem2)
        cocktailItemList.add(cocktailItem3)

        cocktailItem = adminCocktailItemRepository.saveAll(cocktailItemList)
    }

    @Test
    @DisplayName("칵테일 생성 test")
    fun createCocktailTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        item3?.let { itemIdList.add(it.itemId) }

        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail2",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktailTest = adminCocktailService.createCocktail(requestCreateCocktailDTO, "test")

        //then
        assertThat(cocktailTest.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCreateCocktailResponseDTO().httpStatus)
        assertThat(cocktailTest.message).isEqualTo(AdminResponseDTO.toSuccessCreateCocktailResponseDTO().message)
    }

    @Test
    @DisplayName("칵테일 생성시 칵테일에 들어가는 상품을 하나도 고르지 않을 예외 test")
    fun createCocktailExceptionTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail2",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktailTest = adminCocktailService.createCocktail(requestCreateCocktailDTO, "test")

        //then
        assertThat(cocktailTest.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO().httpStatus)
        assertThat(cocktailTest.message).isEqualTo(AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO().message)
    }

    @Test
    @DisplayName("칵테일 생성시 존재하지 않는 상품 예외 test")
    fun formToExistedItemsTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        itemIdList.add(310L)

        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail10",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val cocktailException = adminCocktailService.createCocktail(requestCreateCocktailDTO, "test")

        //then
        assertThat(cocktailException.httpStatus).isEqualTo(AdminResponseDTO.noContentItem().httpStatus)
        assertThat(cocktailException.message).isEqualTo(AdminResponseDTO.noContentItem().message)
    }


    @Test
    @DisplayName("한개의 칵테일 삭제 test")
    fun deleteOneCocktailTest() {
        //when
        val deleteOneCocktail = adminCocktailService.deleteOneCocktail(cocktail!!.cocktailId)

        val optionalCocktail = adminCocktailRepository.findById(cocktail!!.cocktailId)

        //then
        assertThat(deleteOneCocktail.httpStatus).isEqualTo(AdminResponseDTO.toSuccessDeleteCocktailResponseDTO().httpStatus)
        assertThat(deleteOneCocktail.message).isEqualTo(AdminResponseDTO.toSuccessDeleteCocktailResponseDTO().message)

        assertThat(optionalCocktail.orElseThrow().cocktailStatus).isEqualTo(false)
    }

    @Test
    @DisplayName("한개의 칵테일 삭제 예외 test")
    fun deleteOneCocktailExceptionTest() {
        //given
        val cocktailId = 98L

        //when
        val deleteOneCocktail = adminCocktailService.deleteOneCocktail(cocktailId)
        val optionalCocktail = adminCocktailRepository.findById(cocktail!!.cocktailId)

        //then
        assertThat(deleteOneCocktail.httpStatus).isEqualTo(AdminResponseDTO.toFailDeleteCocktailResponseDTO().httpStatus)
        assertThat(deleteOneCocktail.message).isEqualTo(AdminResponseDTO.toFailDeleteCocktailResponseDTO().message)

        assertThat(optionalCocktail.orElseThrow().cocktailStatus).isEqualTo(true)
    }


    @Test
    @DisplayName("칵테일 상품 수정 test")
    fun updateCocktailTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        item3?.let { itemIdList.add(it.itemId) }

        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail1123",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val updateCocktail =
            adminCocktailService.updateCocktail(requestCreateCocktailDTO, cocktail!!.cocktailId, itemIdList, "test")

        val countCocktailItem = adminCocktailItemRepository.countAllByCocktailId(cocktail!!.cocktailId)

        //then
        assertThat(updateCocktail.httpStatus).isEqualTo(AdminResponseDTO.toSuccessUpdateCocktailResponseDTO().httpStatus)
        assertThat(updateCocktail.message).isEqualTo(AdminResponseDTO.toSuccessUpdateCocktailResponseDTO().message)
        assertThat(countCocktailItem).isEqualTo(itemIdList.size.toLong())
    }


    @Test
    @DisplayName("칵테일 상품 수정시 존재하지 않는 칵테일 수정시 예외 test")
    fun updateCocktailNoContentCocktailExceptionTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(100L)


        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val updateCocktail = adminCocktailService.updateCocktail(requestCreateCocktailDTO, 10L, itemIdList, "test")

        //then
        assertThat(updateCocktail.httpStatus).isEqualTo(AdminResponseDTO.noContentItem().httpStatus)
        assertThat(updateCocktail.message).isEqualTo(AdminResponseDTO.noContentItem().message)
    }

    @Test
    @DisplayName("칵테일 상품 수정시 존재하지 않는 item으로 발생하는 예외 test")
    fun updateCocktailNoContentItemExceptionTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        itemIdList.add(100L)

        val cocktailId = cocktail!!.cocktailId

        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        //when
        val updateCocktail = adminCocktailService.updateCocktail(requestCreateCocktailDTO, cocktailId, itemIdList, "test")

        //then
        assertThat(updateCocktail.httpStatus).isEqualTo(AdminResponseDTO.noContentItem().httpStatus)
        assertThat(updateCocktail.message).isEqualTo(AdminResponseDTO.noContentItem().message)
    }

    @Test
    @DisplayName("칵테일 상품 수정시 존재하지 않는 item을 선택하지 않았을 때 발생하는 예외 test")
    fun updateCocktailNoSelectedItemExceptionTest() {
        //given
        val itemIdList: MutableList<Long> = mutableListOf()

        val requestCreateCocktailDTO = CocktailCreateRequestDTO(
            cocktailName = "cocktail1",
            cocktailDescription = "cocktail-description",
            itemIds = itemIdList
        )

        val cocktailId = cocktail!!.cocktailId

        //when
        val updateCocktail = adminCocktailService.updateCocktail(requestCreateCocktailDTO, cocktailId, itemIdList, "test")

        //then
        assertThat(updateCocktail.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO().httpStatus)
        assertThat(updateCocktail.message).isEqualTo(AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO().message)
    }


    @Test
    @DisplayName("칵테일 단건 조회 test")
    fun findCocktailTest() {
        //given
        val cocktailId: Long = cocktail!!.cocktailId

        val itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        item3?.let { itemIdList.add(it.itemId) }

        //when
        val findCocktail = adminCocktailService.findCocktail(cocktailId, itemIdList)

        //then
        assertThat(findCocktail.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(findCocktail.message).isEqualTo("칵테일 조회를 성공했습니다.")
        assertThat(findCocktail.result!!.cocktailName).isEqualTo(cocktail!!.cocktailName)
        assertThat(findCocktail.result!!.cocktailDescription).isEqualTo(cocktail!!.cocktailDescription)
        assertThat(findCocktail.result!!.itemIds).isEqualTo(itemIdList)
    }


    @Test
    @DisplayName("칵테일 단건 조회시 존재하지 않는 칵테일 예외 test")
    fun findCocktailExceptionTest() {
        //given
        val cocktailId: Long = 98L

        val itemIdList: MutableList<Long> = mutableListOf()

        item1?.let { itemIdList.add(it.itemId) }
        item2?.let { itemIdList.add(it.itemId) }
        item3?.let { itemIdList.add(it.itemId) }

        //when
        val findCocktail = adminCocktailService.findCocktail(cocktailId, itemIdList)

        //then
        assertThat(findCocktail.httpStatus).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertThat(findCocktail.message).isEqualTo("존재하지 않는 칵테일입니다.")
    }


    private fun generatePageable(page: Int, pageSize: Int): PageRequest = PageRequest.of(page, pageSize)


    @Test
    @DisplayName("칵테일 전체 조회 test")
    fun getAllCocktailTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize)

        //when
        val allCocktail = adminCocktailService.getAllCocktail(pageable)

        val countCocktail = adminCocktailRepository.countAllByCocktailStatusIsTrue()

        //then
        assertThat(allCocktail.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allCocktail.message).isEqualTo("칵테일 조회를 성공했습니다.")
        assertThat(allCocktail.keyword).isEqualTo("")
        assertThat(allCocktail.result!!.totalElements).isEqualTo(15)
        assertThat(allCocktail.result!!.totalPages).isEqualTo(8)
    }


    @Test
    @DisplayName("칵테일 상품명으로 칵테일 조회 test")
    fun getCocktailsByNameTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize)
        val keyword: String = "1"

        //when
        val allCocktail = adminCocktailService.getCocktailsByName(keyword, pageable)

        //then
        assertThat(allCocktail.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allCocktail.message).isEqualTo("칵테일 조회를 성공했습니다.")
        assertThat(allCocktail.keyword).isEqualTo(keyword)
        assertThat(allCocktail.result!!.totalElements).isEqualTo(6)
        assertThat(allCocktail.result!!.totalPages).isEqualTo(3)
    }


//    @Test
//    @DisplayName("칵테일 상품 삭제 test")
//    fun deleteItemsTest() {
//        //given
//        val cocktailItemList: MutableList<Long> = mutableListOf()
//
//        cocktailItemList.add(cocktail!!.cocktailId)
//
//        val deleteCocktailDTO =  DeleteCocktailDTO(
//            cocktailItemList
//        )
//
//        //when
//        val deleteCocktail = adminCocktailService!!.deleteCocktail(deleteCocktailDTO)
//
//        println("deleteCocktail = ${deleteCocktail.message}")
//
//        //then
//        assertThat(deleteCocktail.status).isEqualTo(setDeleteSuccessCocktailResultDTO().status)
//        assertThat(deleteCocktail.message).isEqualTo(setDeleteSuccessCocktailResultDTO().message)
//
//        val referenceById = cocktail?.let { adminCocktailRepository!!.getReferenceById(it.cocktailId) }
//
//        referenceById?.let { assertThat(it.cocktailStatus).isEqualTo(false) }
//    }
//
//    @Test
//    @DisplayName("존재하지 않는 칵테일을 삭제할 예외 test")
//    fun deleteItemsExceptionTest() {
//        //given
//        val cocktailItemList: MutableList<Long> = mutableListOf()
//
//        cocktail?.let { cocktailItemList.add(it.cocktailId) }
//        cocktailItemList.add(1231L)
//
//        val deleteCocktailDTO =  DeleteCocktailDTO(
//            cocktailItemList
//        )
//
//        //when
//        val deleteCocktail = adminCocktailService.deleteCocktail(deleteCocktailDTO)
//
//        //then
//        assertThat(deleteCocktail.status).isEqualTo(setDeleteFailCocktailResultDTO().status)
//        assertThat(deleteCocktail.message).isEqualTo(setDeleteFailCocktailResultDTO().message)
//
//        val referenceById = cocktail?.let { adminCocktailRepository.getReferenceById(it.cocktailId) }
//
//        referenceById?.let { assertThat(it.cocktailStatus).isEqualTo(true) }
//    }
}
