package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ItemRepository : JpaRepository<Item, Long>,QuerydslPredicateExecutor<Item>{
    fun findItemByCategoryAndItemStatus(category: Category,status: Boolean, pageable: Pageable): Page<Item>

}


