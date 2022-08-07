package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.GetAllOrdersDTO

interface AdminOrderRepositoryCustom {

    fun findOrdersInfo(): List<GetAllOrdersDTO>?

    fun findOrdersInfoByMemberId(keyword: String): List<GetAllOrdersDTO>?
}