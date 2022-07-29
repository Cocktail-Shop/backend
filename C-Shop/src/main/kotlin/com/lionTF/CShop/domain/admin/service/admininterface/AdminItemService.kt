package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.createItemResultDTO

interface AdminItemService {
    fun createItem(createItemDTO: CreateItemDTO): createItemResultDTO
}