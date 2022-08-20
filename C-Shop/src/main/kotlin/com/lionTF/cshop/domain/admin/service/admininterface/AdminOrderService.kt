package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.controller.dto.OrdersSearchDTO
import org.springframework.data.domain.Pageable

interface AdminOrderService {

    fun cancelOneOrder(orderId: Long): AdminResponseDTO

    fun getAllOrders(pageable: Pageable): OrdersSearchDTO

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): OrdersSearchDTO

    fun getAllSales(pageable: Pageable): OrdersSearchDTO

    fun updateDeliveryInDelivery(orderId: Long): AdminResponseDTO

    fun updateDeliveryComplete(orderId: Long): AdminResponseDTO

    //    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO
}