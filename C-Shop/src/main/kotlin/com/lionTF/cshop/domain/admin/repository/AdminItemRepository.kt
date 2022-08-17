package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.controller.dto.ItemResultDTO
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.custom.AdminItemRepositoryCustom
import org.apache.ibatis.annotations.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface AdminItemRepository : JpaRepository<Item, Long>, AdminItemRepositoryCustom {

    @Query(
        "select new com.lionTF.cshop.domain.admin.controller.dto.ItemResultDTO(i.itemName, i.category, i.price, i.amount, i.degree, i.itemDescription, i.itemImgUrl)" +
                " from Item i where i.itemId = :itemId"
    )
    fun findItemById(@Param("itemId") itemId: Long): ItemResultDTO

    fun countAllByItemStatusIsTrue(): Long
}
