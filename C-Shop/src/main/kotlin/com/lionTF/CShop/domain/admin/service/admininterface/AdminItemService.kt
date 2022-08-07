package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*

interface AdminItemService {

    fun createItem(createItemDTO: ItemDTO): CreateItemResultDTO

    fun updateItem(itemId: Long, createItemDTO: ItemDTO): CreateItemResultDTO

    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO

    fun getAllItems(): List<ResponseItemDTO>?
}