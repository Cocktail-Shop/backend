package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*

interface AdminItemService {

    fun createItem(createItemDTO: CreateItemDTO): CreateItemResultDTO

    fun updateItem(itemId: Long, createItemDTO: CreateItemDTO): CreateItemResultDTO

    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO

    fun getAllItems(): List<GetItemDTO>?
}