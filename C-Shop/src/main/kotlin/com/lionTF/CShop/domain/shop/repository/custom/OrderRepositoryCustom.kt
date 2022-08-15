package com.lionTF.CShop.domain.shop.repository.custom

import com.lionTF.CShop.domain.shop.controller.dto.FindShopOrdersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface OrderRepositoryCustom {

    fun findShopOrdersInfo(pageable: Pageable): Page<FindShopOrdersDTO>
}