package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindOrdersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminOrderRepositoryCustom {

    fun findOrdersInfo(pageable: Pageable): Page<FindOrdersDTO>

    fun findOrdersInfoByMemberId(keyword: String, pageable: Pageable): Page<FindOrdersDTO>
}