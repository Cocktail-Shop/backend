package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersResultDTO

interface AdminOrderService {

    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO

    fun getAllOrders(): GetAllOrdersResultDTO
}