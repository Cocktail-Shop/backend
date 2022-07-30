package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.admin.models.Item
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminItemRepository: JpaRepository<Item, Long> {

    // 존재하는 상품이지 상품이름을 통해 검색
    @Query("select i.itemName from Item i where i.itemName = :itemName")
    fun existsByItemName(@Param("itemName") itemName: String): String?

    @Query("select i.itemStatus from Item i where i.itemId = :itemId")
    fun findItemStatusById(@Param("itemId")itemId: Long): Boolean
}