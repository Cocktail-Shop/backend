package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.controller.dto.ResponseSearchOrdersResultDTO
import org.springframework.data.domain.Pageable

interface AdminOrderService {

//    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO

    fun cancelOneOrder(orderId: Long): AdminResponseDTO

    fun getAllOrders(pageable: Pageable): ResponseSearchOrdersResultDTO

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): ResponseSearchOrdersResultDTO
}