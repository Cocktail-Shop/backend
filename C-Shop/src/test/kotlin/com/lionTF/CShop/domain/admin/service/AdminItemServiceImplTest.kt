package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminItemServiceImplTest(

    @Autowired
    private val adminItemService: AdminItemService,

    @Autowired
    private val adminItemRepository: AdminItemRepository
) {

    @Test
    @DisplayName("Item 생성 test")
    fun createItemTest() {
        //given
        var createItemDTO = CreateItemDTO(
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
        var createItemDTO = CreateItemDTO(
            itemName = "test",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다."
        )

        adminItemService.createItem(createItemDTO)

        var createDuplicatedItemDTO = CreateItemDTO(
            itemName = "test",
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
        var item = Item(
            itemName = "test",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        val createItem = adminItemRepository.save(item)

        var updateItemDTO = CreateItemDTO(
            itemName = "test-update",
            category = Category.ALL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val updateItem = adminItemService.updateItem(createItem.itemId, updateItemDTO)

        val getItem = adminItemRepository.findById(item.itemId).get()

        //then
        assertThat(updateItem.status).isEqualTo(setUpdateSuccessItemResultDTO().status)
        assertThat(updateItem.message).isEqualTo(setUpdateSuccessItemResultDTO().message)
        assertThat(getItem.itemId).isEqualTo(item.itemId)
        assertThat(getItem.itemName).isEqualTo(updateItemDTO.itemName)
        assertThat(getItem.category).isEqualTo(updateItemDTO.category)
        assertThat(getItem.price).isEqualTo(updateItemDTO.price)
        assertThat(getItem.amount).isEqualTo(updateItemDTO.amount)
        assertThat(getItem.degree).isEqualTo(updateItemDTO.degree)
        assertThat(getItem.itemDescription).isEqualTo(updateItemDTO.itemDescription)
    }

    @Test
    @DisplayName("상품 수정 예외 test")
    fun updateItemExceptionTest() {
        //given
        var item = Item(
            itemName = "test",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        adminItemRepository.save(item)

        var updateItemDTO = CreateItemDTO(
            itemName = "test-update",
            category = Category.ALL,
            price = 40000,
            amount = 40,
            degree = 10,
            itemDescription = "상품 설명입니다.-update"
        )

        //when
        val updateItem = adminItemService.updateItem(3L, updateItemDTO)

        //then
        assertThat(updateItem.status).isEqualTo(setUpdateFailItemResultDTO().status)
        assertThat(updateItem.message).isEqualTo(setUpdateFailItemResultDTO().message)
    }

    @Test
    @DisplayName("상품 삭제 test")
    fun deleteItemTest() {
        //given
        var item1 = Item(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        adminItemRepository.save(item1)

        var item2 = Item(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        adminItemRepository.save(item2)

        var item3 = Item(
            itemName = "test3",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        adminItemRepository.save(item3)

        var deleteItemIdList = mutableListOf<Long>()

        deleteItemIdList.add(item1.itemId)
        deleteItemIdList.add(item2.itemId)

        var deleteItemDTO = DeleteItemDTO(
            deleteItemIdList
        )
        //when
        val deleteItems = adminItemService.deleteItems(deleteItemDTO)

        //then
        assertThat(deleteItems.status).isEqualTo(setDeleteSuccessItemResultDTO().status)
        assertThat(deleteItems.message).isEqualTo(setDeleteSuccessItemResultDTO().message)

        assertThat(item1.itemStatus).isEqualTo(false)
        assertThat(item2.itemStatus).isEqualTo(false)
        assertThat(item3.itemStatus).isEqualTo(true)
    }

    @Test
    @DisplayName("상품 삭제 예외 test")
    fun deleteItemExceptionTest() {
        //given
        var item1 = Item(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        adminItemRepository.save(item1)

        var item2 = Item(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        adminItemRepository.save(item2)



        var deleteItemIdList = mutableListOf<Long>()

        deleteItemIdList.add(item1.itemId)
        deleteItemIdList.add(10L)

        var deleteItemDTO = DeleteItemDTO(
            deleteItemIdList
        )
        //when
        val deleteItems = adminItemService.deleteItems(deleteItemDTO)

        //then
        assertThat(deleteItems.status).isEqualTo(setDeleteFailItemResultDTO().status)
        assertThat(deleteItems.message).isEqualTo(setDeleteFailItemResultDTO().message)

        assertThat(item1.itemStatus).isEqualTo(false)
        assertThat(item2.itemStatus).isEqualTo(true)
    }
}