package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.OrdersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminOrderRepositoryCustom {

    fun findOrdersInfo(pageable: Pageable): Page<OrdersDTO>

    fun findOrdersInfoByKeyword(keyword: String, pageable: Pageable): Page<OrdersDTO>

    fun findOrdersInfoByMemberId(memberId: Long, pageable: Pageable): Page<OrdersDTO>


}
