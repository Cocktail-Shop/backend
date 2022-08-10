package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindOrders
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminOrderService {

    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO

    fun getAllOrders(pageable: Pageable): Page<FindOrders>

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): Page<FindOrders>
}