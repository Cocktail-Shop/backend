package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.controller.dto.ItemResultDTO
import com.lionTF.cshop.domain.admin.controller.dto.ItemsDTO
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.custom.AdminItemRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AdminItemRepository : JpaRepository<Item, Long>, AdminItemRepositoryCustom {

    @Query(
        "select new com.lionTF.cshop.domain.admin.controller.dto.ItemResultDTO(i.itemName, i.category, i.price, i.amount, i.degree, i.itemDescription, i.itemImgUrl)" +
                " from Item i where i.itemId = :itemId"
    )
    fun findItemById(@Param("itemId") itemId: Long): ItemResultDTO

    fun countAllByItemStatusIsTrue(): Long

    @Query("select count(i) from Item i where i.itemId = :itemId")
    fun countItemStatusIsFalse(@Param("itemId") itemId: Long): Int

    @Query("select i from Item i where i.itemId = :itemId and i.itemStatus = :itemStatus")
    fun findItem(@Param("itemId") itemId: Long, @Param("itemStatus") itemStatus: Boolean): Item?

    @Query("select new com.lionTF.cshop.domain.admin.controller.dto.ItemsDTO(i.itemId, i.itemName, i.price, i.amount, i.degree, i.itemDescription, i.itemImgUrl, i.category, i.createdAt)" +
            " from Item i where i.itemStatus = :itemStatus")
    fun getItems(@Param("itemStatus") itemStatus: Boolean): List<ItemsDTO>
}
