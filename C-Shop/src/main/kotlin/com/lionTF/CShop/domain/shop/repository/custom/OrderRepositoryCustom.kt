package com.lionTF.CShop.domain.shop.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindOrdersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderRepositoryCustom {

    fun findOrdersInfo(pageable: Pageable): Page<FindOrdersDTO>
}