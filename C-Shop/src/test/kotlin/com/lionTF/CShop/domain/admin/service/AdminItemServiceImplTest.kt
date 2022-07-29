package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.setCreateFailItemResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setCreateSuccessItemResultDTO
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
}