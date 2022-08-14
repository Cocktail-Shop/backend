package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindItemDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminItemRepositoryCustom {

    fun findAllItems(pageable: Pageable): Page<FindItemDTO>

    fun findItemsByName(keyword: String, pageable: Pageable): Page<FindItemDTO>
}