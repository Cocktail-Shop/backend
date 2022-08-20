package com.lionTF.cshop.domain.admin.models

import com.lionTF.cshop.domain.admin.controller.dto.ItemCreateRequestDTO
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.annotation.Rollback

@Rollback
internal class ItemUnitTest {

    private var item = Item(
        itemId = 1L,
        itemName = "앱솔",
        price = 10000,
        amount = 100,
        degree =  50,
        itemDescription = "test",
        itemImgUrl = "testUrl",
        category = Category.ALCOHOL,
        itemStatus = true
    )


    @Test
    @DisplayName("상품 수정 test")
    fun updateTest() {
        val requestCreateItemDTO = ItemCreateRequestDTO(
            itemName = "test-update",
            category = Category.NONALCOHOL,
            price = 20000,
            amount = 2000,
            degree = 40,
            itemDescription = "test-update"
        )

        //when
        item.update(requestCreateItemDTO, "test")

        //then
        assertThat(item.itemName).isEqualTo(requestCreateItemDTO.itemName)
        assertThat(item.category).isEqualTo(requestCreateItemDTO.category)
        assertThat(item.price).isEqualTo(requestCreateItemDTO.price)
        assertThat(item.amount).isEqualTo(requestCreateItemDTO.amount)
        assertThat(item.degree).isEqualTo(requestCreateItemDTO.degree)
        assertThat(item.itemDescription).isEqualTo(requestCreateItemDTO.itemDescription)
    }

    @Test
    @DisplayName("상품 삭제 test")
    fun deleteTest() {
        //when
        item.delete()

        //then
        assertThat(item.itemStatus).isEqualTo(false)
        println("item.itemStatus = ${item.itemStatus}")
    }
    
    @Test
    @DisplayName("상품 취소 시 상품 수량 복귀 test")
    fun addAmountTest() {
        //given
        val quantity = 10

        //when
        item.addAmount(quantity)
        
        //then
        assertThat(item.amount).isEqualTo(110)
    } 
}
