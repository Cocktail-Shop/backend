package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminItemServiceTest {

    @Autowired
    private lateinit var adminItemService: AdminItemService

    @Autowired
    private lateinit var adminItemRepository: AdminItemRepository

    private var item1: Item? = null
    private var item2: Item? = null
    private var item3: Item? = null
    private var item4: Item? = null

    @BeforeEach
    fun init() {
        var itemTest1 = Item(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item1 = adminItemRepository.save(itemTest1)

        var itemTest2 = Item(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item2 = adminItemRepository.save(itemTest2)

        var itemTest3 = Item(
            itemName = "test3",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item3 = adminItemRepository.save(itemTest3)


        var itemTest4 = Item(
            itemName = "test4",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item4 = adminItemRepository.save(itemTest4)
    }

    @Test
    @DisplayName("Item 생성 test")
    fun createItemTest() {
        //given
        var createItemDTO = RequestCreateItemDTO(
            itemName = "test",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItem = adminItemService.createItem(createItemDTO)

        //then
        assertThat(createItem.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCreateItemResponseDTO().httpStatus)
        assertThat(createItem.message).isEqualTo(AdminResponseDTO.toSuccessCreateItemResponseDTO().message)
    }

    @Test
    @DisplayName("Item 생성 예외 중 가격이 0이하인 case test")
    fun createItemExceptionByPriceTest() {
        //given
        var createInvalidFormatPrice = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 0,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItemExceptionByPrice = adminItemService.createItem(createInvalidFormatPrice)

        //then
        assertThat(createItemExceptionByPrice.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().httpStatus)
        assertThat(createItemExceptionByPrice.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().message)
        println("createItemExceptionByPrice = ${createItemExceptionByPrice.message}")
    }

    @Test
    @DisplayName("Item 생성 예외 중 수량이 0이하인 case test")
    fun createItemExceptionByAmountTest() {
        //given
        var createInvalidFormatAmount = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10,
            amount = 0,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItemExceptionByAmount = adminItemService.createItem(createInvalidFormatAmount)

        //then
        assertThat(createItemExceptionByAmount.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().httpStatus)
        assertThat(createItemExceptionByAmount.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().message)
        println("createItemExceptionByAmount = ${createItemExceptionByAmount.message}")
    }

    @Test
    @DisplayName("Item 생성 예외 중 수량과 가격이 0이하인 case test")
    fun createItemExceptionByAmountAndPriceTest() {
        //given
        var createInvalidFormatAmountAndPrice = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 0,
            amount = 0,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItemExceptionByAmountAndPrice = adminItemService.createItem(createInvalidFormatAmountAndPrice)

        //then
        assertThat(createItemExceptionByAmountAndPrice.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().httpStatus)
        assertThat(createItemExceptionByAmountAndPrice.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().message)
        println("createItemExceptionByAmountAndPrice = ${createItemExceptionByAmountAndPrice.message}")
    }

    @Test
    @DisplayName("상품 수정 test")
    fun updateItemTest() {
        //given
        var updateItemDTO = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val updateItem = item1?.let { adminItemService.updateItem(it.itemId, updateItemDTO) }

        val getItem = item1?.let { adminItemRepository.findById(it.itemId).get() }

        //then
        assertThat(updateItem?.httpStatus).isEqualTo(AdminResponseDTO.toSuccessUpdateItemResponseDTO().httpStatus)
        assertThat(updateItem?.message).isEqualTo(AdminResponseDTO.toSuccessUpdateItemResponseDTO().message)

        assertThat(getItem?.itemId).isEqualTo(item1?.itemId)
        assertThat(getItem?.itemName).isEqualTo(updateItemDTO.itemName)
        assertThat(getItem?.category).isEqualTo(updateItemDTO.category)
        assertThat(getItem?.price).isEqualTo(updateItemDTO.price)
        assertThat(getItem?.amount).isEqualTo(updateItemDTO.amount)
        assertThat(getItem?.degree).isEqualTo(updateItemDTO.degree)
        assertThat(getItem?.itemDescription).isEqualTo(updateItemDTO.itemDescription)
    }

    @Test
    @DisplayName("상품 수정 예외 중 없는 상품 case test")
    fun updateItemExceptionTest() {
        //given
        var invalidIpdateItemDTO = RequestCreateItemDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidUpdateItem = adminItemService.updateItem(10L, invalidIpdateItemDTO)

        //then
        assertThat(invalidUpdateItem.httpStatus).isEqualTo(AdminResponseDTO.toFailUpdateItemResponseDTO().httpStatus)
        assertThat(invalidUpdateItem.message).isEqualTo(AdminResponseDTO.toFailUpdateItemResponseDTO().message)
        println("updateItem.message = ${invalidUpdateItem.message}")
    }

    @Test
    @DisplayName("상품 수정 예외 중 가격이 0이하인 case test")
    fun updateItemExceptionByPriceTest() {
        //given
        var invalidPriceUpdateItemDTO = RequestCreateItemDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 0,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidPriceUpdateItem = adminItemService.updateItem(item1!!.itemId, invalidPriceUpdateItemDTO)

        //then
        assertThat(invalidPriceUpdateItem.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().httpStatus)
        assertThat(invalidPriceUpdateItem.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().message)
        println("invalidPriceUpdateItem.message = ${invalidPriceUpdateItem.message}")
    }

    @Test
    @DisplayName("상품 수정 예외 중 수량이 0이하인 case test")
    fun updateItemExceptionByAmountTest() {
        //given
        var invalidAmountUpdateItemDTO = RequestCreateItemDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 10,
            amount = 0,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidAmountUpdateItem = adminItemService.updateItem(item1!!.itemId, invalidAmountUpdateItemDTO)

        //then
        assertThat(invalidAmountUpdateItem.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().httpStatus)
        assertThat(invalidAmountUpdateItem.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().message)
        println("invalidAmountUpdateItem.message = ${invalidAmountUpdateItem.message}")
    }

    @Test
    @DisplayName("상품 수정 예외 중 수량과 가격 0이하인 case test")
    fun updateItemExceptionByAmountAndPriceTest() {
        //given
        var invalidAmountAndPriceUpdateItemDTO = RequestCreateItemDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 0,
            amount = 0,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidAmountAndPriceUpdateItem = adminItemService.updateItem(item1!!.itemId, invalidAmountAndPriceUpdateItemDTO)

        //then
        assertThat(invalidAmountAndPriceUpdateItem.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().httpStatus)
        assertThat(invalidAmountAndPriceUpdateItem.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().message)
        println("invalidAmountAndPriceUpdateItem.message = ${invalidAmountAndPriceUpdateItem.message}")
    }


    @Test
    @DisplayName("하나의 상품 삭제 Test")
    fun deleteOneItemTest() {
        //given

        //when
        val deleteOneItem = adminItemService.deleteOneItem(item1!!.itemId)

        //then
        assertThat(deleteOneItem.httpStatus).isEqualTo(AdminResponseDTO.toSuccessDeleteItemResponseDTO().httpStatus)
        assertThat(deleteOneItem.message).isEqualTo(AdminResponseDTO.toSuccessDeleteItemResponseDTO().message)
        assertThat(item1!!.itemStatus).isEqualTo(false)
        println("deleteOneItem = ${deleteOneItem.message}")
    }

    @Test
    @DisplayName("하나의 상품 삭제 예외중 없는 아이템일 case Test")
    fun deleteOneItemExceptionTest() {
        //given
        var itemID: Long = 98L

        //when
        val invalidItemIdDeleteOneItem = adminItemService.deleteOneItem(itemID)

        //then
        assertThat(invalidItemIdDeleteOneItem.httpStatus).isEqualTo(AdminResponseDTO.toFailDeleteItemResponseDTO().httpStatus)
        assertThat(invalidItemIdDeleteOneItem.message).isEqualTo(AdminResponseDTO.toFailDeleteItemResponseDTO().message)
        assertThat(item1!!.itemStatus).isEqualTo(true)
        println("invalidItemIdDeleteOneItem = ${invalidItemIdDeleteOneItem.message}")
    }

    @Test
    @DisplayName("상품 단건 조회 test")
    fun findItemTest() {
        //given
        val itemId = item1!!.itemId

        //when
        val findItem = adminItemService.findItem(itemId)

        //then
        assertThat(findItem.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(findItem.message).isEqualTo("상품 조회를 성공했습니다.")
        assertThat(findItem.result!!.itemName).isEqualTo(item1!!.itemName)
        assertThat(findItem.result!!.itemDescription).isEqualTo(item1!!.itemDescription)
        assertThat(findItem.result!!.amount).isEqualTo(item1!!.amount)
        assertThat(findItem.result!!.category).isEqualTo(item1!!.category)
    }


    @Test
    @DisplayName("상품 단건 조회 예외 test")
    fun findItemExceptionTest() {
        //given
        val itemId = 98L

        //when
        val findItem = adminItemService.findItem(itemId)

        //then
        assertThat(findItem.httpStatus).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertThat(findItem.message).isEqualTo("존재 하지 않는 상품입니다.")
    }

    private fun generatePageable(page: Int, pageSize: Int): PageRequest = PageRequest.of(page, pageSize)

    @Test
    @DisplayName("상품 전체 조회 test")
    fun getAllItemsTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize,)

        //when
        val allItems = adminItemService.getAllItems(pageable)

        //then
        assertThat(allItems.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allItems.message).isEqualTo("상품 조회를 성공했습니다.")
        assertThat(allItems.keyword).isEqualTo("")
        assertThat(allItems.result!!.totalElements).isEqualTo(11)
        assertThat(allItems.result!!.totalPages).isEqualTo(6)
    }

    @Test
    @DisplayName("상품명으로 상품 조회 test")
    fun getItemsByNameTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize,)
        val keyword: String = "te"

        //when
        val allItems = adminItemService.getItemsByName(keyword, pageable)

        //then
        assertThat(allItems.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allItems.message).isEqualTo("상품 조회를 성공했습니다.")
        assertThat(allItems.keyword).isEqualTo(keyword)
        assertThat(allItems.result!!.totalElements).isEqualTo(5)
        assertThat(allItems.result!!.totalPages).isEqualTo(3)
    }

    //    @Test
//    @DisplayName("상품 삭제 test")
//    fun deleteItemTest() {
//        //given
//        var deleteItemIdList = mutableListOf<Long>()
//
//        item1?.let { deleteItemIdList.add(it.itemId) }
//        item2?.let { deleteItemIdList.add(it.itemId) }
//
//        var deleteItemDTO = DeleteItemDTO(
//            deleteItemIdList
//        )
//        //when
//        val deleteItems = adminItemService.deleteItems(deleteItemDTO)
//
//        //then
//        assertThat(deleteItems.status).isEqualTo(setDeleteSuccessItemResultDTO().status)
//        assertThat(deleteItems.message).isEqualTo(setDeleteSuccessItemResultDTO().message)
//
//        item1?.let { assertThat(it.itemStatus).isEqualTo(false) }
//        item2?.let { assertThat(it.itemStatus).isEqualTo(false) }
//        item3?.let { assertThat(it.itemStatus).isEqualTo(true) }
//    }

//    @Test
//    @DisplayName("상품 삭제 예외 test")
//    fun deleteItemExceptionTest() {
//        //given
//        var deleteItemIdList = mutableListOf<Long>()
//
//        item1?.let { deleteItemIdList.add(it.itemId) }
//        deleteItemIdList.add(10L)  // 등록되지 않은 상품
//
//        var deleteItemDTO = DeleteItemDTO(
//            deleteItemIdList
//        )
//        //when
//        val deleteItems = adminItemService.deleteItems(deleteItemDTO)
//
//        //then
//        assertThat(deleteItems.status).isEqualTo(setDeleteFailItemResultDTO().status)
//        assertThat(deleteItems.message).isEqualTo(setDeleteFailItemResultDTO().message)
//
//        item1?.let { assertThat(it.itemStatus).isEqualTo(false) }
//        item2?.let { assertThat(it.itemStatus).isEqualTo(true) }
//    }
}