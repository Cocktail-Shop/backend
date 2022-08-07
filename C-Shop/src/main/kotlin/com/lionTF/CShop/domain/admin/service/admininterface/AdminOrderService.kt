package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersResultDTO
import org.springframework.data.domain.Pageable

interface AdminOrderService {

    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO

    fun getAllOrders(pageable: Pageable): GetAllOrdersResultDTO

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): GetAllOrdersResultDTO
}