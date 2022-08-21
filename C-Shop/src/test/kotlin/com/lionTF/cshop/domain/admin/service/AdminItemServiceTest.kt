package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminItemService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminItemServiceTest {

    @Autowired
    private val adminItemService: AdminItemService? = null

    @Autowired
    private val adminItemRepository: AdminItemRepository? = null

    private var item1: Item? = null
    private var item2: Item? = null
    private var item3: Item? = null
    private var item4: Item? = null

    @BeforeEach
    fun init() {
        val itemTest1 = Item(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item1 = adminItemRepository?.save(itemTest1)

        val itemTest2 = Item(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item2 = adminItemRepository?.save(itemTest2)

        val itemTest3 = Item(
            itemName = "test3",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item3 = adminItemRepository?.save(itemTest3)


        val itemTest4 = Item(
            itemName = "test4",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item4 = adminItemRepository?.save(itemTest4)
    }

    @Test
    @DisplayName("Item 생성 test")
    fun createItemTest() {
        //given
        val createItemDTO = ItemCreateRequestDTO(
            itemName = "test",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItem = adminItemService?.createItem(createItemDTO, "test")

        //then
        assertThat(createItem?.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCreateItemResponseDTO().httpStatus)
        assertThat(createItem?.message).isEqualTo(AdminResponseDTO.toSuccessCreateItemResponseDTO().message)
    }

    @Test
    @DisplayName("Item 생성 예외 중 가격이 0이하인 case test")
    fun createItemExceptionByPriceTest() {
        //given
        val createInvalidFormatPrice = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 0,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItemExceptionByPrice = adminItemService?.createItem(createInvalidFormatPrice, "test")

        //then
        assertThat(createItemExceptionByPrice?.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().httpStatus)
        assertThat(createItemExceptionByPrice?.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().message)
        println("createItemExceptionByPrice = ${createItemExceptionByPrice?.message}")
    }

    @Test
    @DisplayName("Item 생성 예외 중 수량이 0이하인 case test")
    fun createItemExceptionByAmountTest() {
        //given
        val createInvalidFormatAmount = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10,
            amount = 0,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItemExceptionByAmount = adminItemService?.createItem(createInvalidFormatAmount, "test")

        //then
        assertThat(createItemExceptionByAmount?.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().httpStatus)
        assertThat(createItemExceptionByAmount?.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().message)
        println("createItemExceptionByAmount = ${createItemExceptionByAmount?.message}")
    }

    @Test
    @DisplayName("Item 생성 예외 중 수량과 가격이 0이하인 case test")
    fun createItemExceptionByAmountAndPriceTest() {
        //given
        val createInvalidFormatAmountAndPrice = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 0,
            amount = 0,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val createItemExceptionByAmountAndPrice = adminItemService?.createItem(createInvalidFormatAmountAndPrice, "test")

        //then
        assertThat(createItemExceptionByAmountAndPrice?.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().httpStatus)
        assertThat(createItemExceptionByAmountAndPrice?.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().message)
        println("createItemExceptionByAmountAndPrice = ${createItemExceptionByAmountAndPrice?.message}")
    }

    @Test
    @DisplayName("상품 수정 test")
    fun updateItemTest() {
        //given
        val updateItemDTO = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val updateItem = item1?.let { adminItemService?.updateItem(it.itemId, updateItemDTO, "test") }

        val getItem = item1?.let { adminItemRepository?.findById(it.itemId)?.get() }

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
        val invalidUpdateItemDTO = ItemCreateRequestDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidUpdateItem = adminItemService?.updateItem(12312312412L, invalidUpdateItemDTO, "test")

        //then
        assertThat(invalidUpdateItem?.httpStatus).isEqualTo(AdminResponseDTO.toFailUpdateItemResponseDTO().httpStatus)
        assertThat(invalidUpdateItem?.message).isEqualTo(AdminResponseDTO.toFailUpdateItemResponseDTO().message)
        println("updateItem.message = ${invalidUpdateItem?.message}")
    }

    @Test
    @DisplayName("상품 수정 예외 중 가격이 0이하인 case test")
    fun updateItemExceptionByPriceTest() {
        //given
        val invalidPriceUpdateItemDTO = ItemCreateRequestDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 0,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidPriceUpdateItem = item1?.let { adminItemService?.updateItem(it.itemId, invalidPriceUpdateItemDTO, "test") }

        //then
        assertThat(invalidPriceUpdateItem?.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().httpStatus)
        assertThat(invalidPriceUpdateItem?.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO().message)
        println("invalidPriceUpdateItem.message = ${invalidPriceUpdateItem?.message}")
    }

    @Test
    @DisplayName("상품 수정 예외 중 수량이 0이하인 case test")
    fun updateItemExceptionByAmountTest() {
        //given
        val invalidAmountUpdateItemDTO = ItemCreateRequestDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 10,
            amount = 0,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidAmountUpdateItem = item1?.let { adminItemService?.updateItem(it.itemId, invalidAmountUpdateItemDTO, "test") }

        //then
        assertThat(invalidAmountUpdateItem?.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().httpStatus)
        assertThat(invalidAmountUpdateItem?.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO().message)
        println("invalidAmountUpdateItem.message = ${invalidAmountUpdateItem?.message}")
    }

    @Test
    @DisplayName("상품 수정 예외 중 수량과 가격 0이하인 case test")
    fun updateItemExceptionByAmountAndPriceTest() {
        //given
        val invalidAmountAndPriceUpdateItemDTO = ItemCreateRequestDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 0,
            amount = 0,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val invalidAmountAndPriceUpdateItem =
            item1?.let { adminItemService?.updateItem(it.itemId, invalidAmountAndPriceUpdateItemDTO, "test") }

        //then
        assertThat(invalidAmountAndPriceUpdateItem?.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().httpStatus)
        assertThat(invalidAmountAndPriceUpdateItem?.message).isEqualTo(AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO().message)
        println("invalidAmountAndPriceUpdateItem.message = ${invalidAmountAndPriceUpdateItem?.message}")
    }


    @Test
    @DisplayName("하나의 상품 삭제 Test")
    fun deleteOneItemTest() {
        //given

        //when
        val deleteOneItem = item1?.let { adminItemService?.deleteOneItem(it.itemId) }

        //then
        assertThat(deleteOneItem?.httpStatus).isEqualTo(AdminResponseDTO.toSuccessDeleteItemResponseDTO().httpStatus)
        assertThat(deleteOneItem?.message).isEqualTo(AdminResponseDTO.toSuccessDeleteItemResponseDTO().message)
        item1?.let { assertThat(it.itemStatus).isEqualTo(false) }
        println("deleteOneItem = ${deleteOneItem?.message}")
    }

    @Test
    @DisplayName("하나의 상품 삭제 예외중 없는 아이템일 case Test")
    fun deleteOneItemExceptionTest() {
        //given
        val itemID = 98L

        //when
        val invalidItemIdDeleteOneItem = adminItemService?.deleteOneItem(itemID)

        //then
        assertThat(invalidItemIdDeleteOneItem?.httpStatus).isEqualTo(AdminResponseDTO.toFailDeleteItemResponseDTO().httpStatus)
        assertThat(invalidItemIdDeleteOneItem?.message).isEqualTo(AdminResponseDTO.toFailDeleteItemResponseDTO().message)
        item1?.let { assertThat(it.itemStatus).isEqualTo(true) }
        println("invalidItemIdDeleteOneItem = ${invalidItemIdDeleteOneItem?.message}")
    }

    @Test
    @DisplayName("상품 단건 조회 test")
    fun findItemTest() {
        //given
        val itemId = item1?.itemId

        //when
        val findItem = itemId?.let { adminItemService?.findItem(it) }

        //then
        assertThat(findItem?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(findItem?.message).isEqualTo("상품 조회를 성공했습니다.")
        assertThat(findItem?.result?.itemName).isEqualTo(item1?.itemName)
        assertThat(findItem?.result?.itemDescription).isEqualTo(item1?.itemDescription)
        assertThat(findItem?.result?.amount).isEqualTo(item1?.amount)
        assertThat(findItem?.result?.category).isEqualTo(item1?.category)
    }


    @Test
    @DisplayName("상품 단건 조회 예외 test")
    fun findItemExceptionTest() {
        //given
        val itemId = 98L

        //when
        val findItem = adminItemService?.findItem(itemId)

        //then
        assertThat(findItem?.httpStatus).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        assertThat(findItem?.message).isEqualTo("존재 하지 않는 상품입니다.")
    }

    private fun generatePageable(page: Int = 0, pageSize: Int = 2): PageRequest = PageRequest.of(page, pageSize)

    @Test
    @DisplayName("상품 전체 조회 test")
    fun getAllItemsTest() {
        //given
        val pageable = generatePageable()

        //when
        val allItems = adminItemService?.getAllItems(pageable)

        val count = adminItemRepository?.countAllByItemStatusIsTrue()

        //then
        assertThat(allItems?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allItems?.message).isEqualTo("상품 조회를 성공했습니다.")
        assertThat(allItems?.itemName).isEqualTo("")
        assertThat(allItems?.result?.totalElements).isEqualTo(count)
        assertThat(allItems?.result?.totalPages).isEqualTo((count?.div(2))?.plus(1))
    }

    @Test
    @DisplayName("상품명으로 상품 조회 test")
    fun getItemsByNameTest() {
        //given
        val pageable = generatePageable()
        val keyword = "te"

        //when
        val allItems = adminItemService?.getItemsByName(keyword, pageable)

        //then
        assertThat(allItems?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allItems?.message).isEqualTo("상품 조회를 성공했습니다.")
        assertThat(allItems?.itemName).isEqualTo(keyword)
        assertThat(allItems?.result?.totalElements).isEqualTo(8)
        assertThat(allItems?.result?.totalPages).isEqualTo(4)
    }
}
