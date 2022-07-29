package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.createItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.createItemResultDTO

interface AdminItemService {
    fun createItem(createItemDTO: createItemDTO): createItemResultDTO
}