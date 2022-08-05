package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersResultDTO

interface AdminOrderService {

    fun deleteOrders(deleteOrdersDTO: DeleteOrdersDTO): DeleteOrdersResultDTO
}