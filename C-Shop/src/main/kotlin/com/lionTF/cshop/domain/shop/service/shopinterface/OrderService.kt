package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.admin.controller.dto.OrdersSearchDTO
import com.lionTF.cshop.domain.member.controller.dto.AddressDTO
import com.lionTF.cshop.domain.shop.controller.dto.CartCancelResponseDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderDTO
import org.springframework.data.domain.Pageable

interface OrderService {

    fun requestOrder(requestOrderDTO: RequestOrderDTO): Any

    fun getAddress(memberId: Long): AddressDTO

    fun getShopOrders(memberId: Long, pageable: Pageable): OrdersSearchDTO

    fun cancelOneOrder(orderId: Long): CartCancelResponseDTO
}
