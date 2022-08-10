package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindOrders
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminOrderRepositoryCustom {

    fun findOrdersInfo(pageable: Pageable): Page<FindOrders>

    fun findOrdersInfoByMemberId(keyword: String, pageable: Pageable): Page<FindOrders>
}