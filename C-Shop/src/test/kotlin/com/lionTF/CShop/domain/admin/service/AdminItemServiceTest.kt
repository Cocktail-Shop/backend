package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
    }

    @Test
    @DisplayName("Item 생성 test")
    fun createItemTest() {
        //given
        var createItemDTO = ItemDTO(
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
        assertThat(createItem.status).isEqualTo(setCreateSuccessItemResultDTO().status)
        assertThat(createItem.message).isEqualTo(setCreateSuccessItemResultDTO().message)
    }

    @Test
    @DisplayName("Item 생성 예외 test")
    fun createItemExceptionTest() {
        //given
        var createDuplicatedItemDTO = ItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        //when
        val duplicatedItem = adminItemService.createItem(createDuplicatedItemDTO)

        //then
        assertThat(duplicatedItem.status).isEqualTo(setCreateFailItemResultDTO().status)
        assertThat(duplicatedItem.message).isEqualTo(setCreateFailItemResultDTO().message)
    }

    @Test
    @DisplayName("상품 수정 test")
    fun updateItemTest() {
        //given
        var updateItemDTO = ItemDTO(
            itemName = "test-update",
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
        assertThat(updateItem?.status).isEqualTo(setUpdateSuccessItemResultDTO().status)
        assertThat(updateItem?.message).isEqualTo(setUpdateSuccessItemResultDTO().message)
        assertThat(getItem?.itemId).isEqualTo(item1?.itemId)
        assertThat(getItem?.itemName).isEqualTo(updateItemDTO.itemName)
        assertThat(getItem?.category).isEqualTo(updateItemDTO.category)
        assertThat(getItem?.price).isEqualTo(updateItemDTO.price)
        assertThat(getItem?.amount).isEqualTo(updateItemDTO.amount)
        assertThat(getItem?.degree).isEqualTo(updateItemDTO.degree)
        assertThat(getItem?.itemDescription).isEqualTo(updateItemDTO.itemDescription)
    }

    @Test
    @DisplayName("상품 수정 예외 test")
    fun updateItemExceptionTest() {
        //given
        var updateItemDTO = ItemDTO(
            itemName = "test-update",
            category = Category.ALCOHOL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val updateItem = adminItemService.updateItem(10L, updateItemDTO)

        //then
        assertThat(updateItem.status).isEqualTo(setUpdateFailItemResultDTO().status)
        assertThat(updateItem.message).isEqualTo(setUpdateFailItemResultDTO().message)
    }

    @Test
    @DisplayName("상품 삭제 test")
    fun deleteItemTest() {
        //given
        var deleteItemIdList = mutableListOf<Long>()

        item1?.let { deleteItemIdList.add(it.itemId) }
        item2?.let { deleteItemIdList.add(it.itemId) }

        var deleteItemDTO = DeleteItemDTO(
            deleteItemIdList
        )
        //when
        val deleteItems = adminItemService.deleteItems(deleteItemDTO)

        //then
        assertThat(deleteItems.status).isEqualTo(setDeleteSuccessItemResultDTO().status)
        assertThat(deleteItems.message).isEqualTo(setDeleteSuccessItemResultDTO().message)

        item1?.let { assertThat(it.itemStatus).isEqualTo(false) }
        item2?.let { assertThat(it.itemStatus).isEqualTo(false) }
        item3?.let { assertThat(it.itemStatus).isEqualTo(true) }
    }

    @Test
    @DisplayName("상품 삭제 예외 test")
    fun deleteItemExceptionTest() {
        //given
        var deleteItemIdList = mutableListOf<Long>()

        item1?.let { deleteItemIdList.add(it.itemId) }
        deleteItemIdList.add(10L)  // 등록되지 않은 상품

        var deleteItemDTO = DeleteItemDTO(
            deleteItemIdList
        )
        //when
        val deleteItems = adminItemService.deleteItems(deleteItemDTO)

        //then
        assertThat(deleteItems.status).isEqualTo(setDeleteFailItemResultDTO().status)
        assertThat(deleteItems.message).isEqualTo(setDeleteFailItemResultDTO().message)

        item1?.let { assertThat(it.itemStatus).isEqualTo(false) }
        item2?.let { assertThat(it.itemStatus).isEqualTo(true) }
    }


    @Test
    @DisplayName("상품 삭제 후 같은 이름의 상품이 등록되는지 test")
    fun deleteAndCreateItemTest() {
        //given
        var deleteItemIdList = mutableListOf<Long>()

        item1?.let { deleteItemIdList.add(it.itemId) }

        var deleteItemDTO = DeleteItemDTO(
            deleteItemIdList
        )

        adminItemService.deleteItems(deleteItemDTO)

        //when
        var createItemDTO = ItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        val createItem = adminItemService.createItem(createItemDTO)

        //then
        assertThat(createItem.status).isEqualTo(setCreateSuccessItemResultDTO().status)
        assertThat(createItem.message).isEqualTo(setCreateSuccessItemResultDTO().message)
    }
}