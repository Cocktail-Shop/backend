package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseAllOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseAllOrdersResultDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminOrderService {

    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO

    fun getAllOrders(pageable: Pageable): Page<ResponseAllOrdersDTO>

    fun getOrdersByMemberId(keyword: String, pageable: Pageable): ResponseAllOrdersResultDTO
}