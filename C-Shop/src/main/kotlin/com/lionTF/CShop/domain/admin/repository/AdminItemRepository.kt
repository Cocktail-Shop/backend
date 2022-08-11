package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.custom.AdminItemRepositoryCustom
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminItemRepository: JpaRepository<Item, Long>, AdminItemRepositoryCustom {

    // 존재하는 상품이지 상품이름을 통해 검색
    @Query(
        "select i.itemName from Item i" +
                " where i.itemName = :itemName and i.itemStatus = :itemStatus and i.degree = :degree"
    )
    fun existsByItemName(
        @Param("itemName") itemName: String,
        @Param("itemStatus") itemStatus: Boolean,
        @Param("degree") degree: Int
    ): String?


    @Query("select i.itemStatus from Item i where i.itemId = :itemId")
    fun findItemStatusById(@Param("itemId")itemId: Long): Boolean?

    @Query("select i from Item i where i.itemStatus = :itemStatus")
    fun findAllByItemStatusTrue(@Param("itemStatus")itemStatus: Boolean): List<Item>?
}