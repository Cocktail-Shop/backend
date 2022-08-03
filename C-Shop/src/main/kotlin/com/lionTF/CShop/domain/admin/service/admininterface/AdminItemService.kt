package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteItemResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateItemResultDTO

interface AdminItemService {

    fun createItem(createItemDTO: CreateItemDTO): CreateItemResultDTO

    fun updateItem(itemId: Long, createItemDTO: CreateItemDTO): CreateItemResultDTO

    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO
}