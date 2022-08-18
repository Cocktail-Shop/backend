package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.controller.dto.ResponseSearchOrdersResultDTO
import org.springframework.data.domain.Pageable

interface AdminOrderService {

    fun cancelOneOrder(orderId: Long): AdminResponseDTO

    fun getAllOrders(pageable: Pageable): ResponseSearchOrdersResultDTO

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): ResponseSearchOrdersResultDTO

    fun getAllSales(pageable: Pageable): ResponseSearchOrdersResultDTO

    fun changeDeliveryReady(orderId: Long): AdminResponseDTO

    //    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO
}