package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseSearchItemSearchDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseSearchOrdersResultDTO
import org.springframework.data.domain.Pageable

interface AdminOrderService {

//    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO

    fun deleteOneOrder(orderId: Long): AdminResponseDTO

    fun getAllOrders(pageable: Pageable): ResponseSearchOrdersResultDTO

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): ResponseSearchOrdersResultDTO
}