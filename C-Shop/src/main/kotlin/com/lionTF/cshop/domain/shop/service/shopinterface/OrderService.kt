package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.admin.controller.dto.OrdersSearchDTO
import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderResultDTO
import org.springframework.data.domain.Pageable

interface OrderService {

    fun requestOrder(requestOrderDTO: RequestOrderDTO) : RequestOrderResultDTO

    fun getAddress(memberId: Long) : AddressDTO

    fun getShopOrders(pageable: Pageable): OrdersSearchDTO
}
