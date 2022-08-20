package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.ItemsDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminItemRepositoryCustom {

    fun findAllItems(pageable: Pageable): Page<ItemsDTO>

    fun findItemsByName(itemName: String, pageable: Pageable): Page<ItemsDTO>
}
