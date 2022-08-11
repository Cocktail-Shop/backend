package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminItemService {

    fun createItem(requestCreateItemDTO: RequestCreateItemDTO): AdminResponseDTO

    fun updateItem(itemId: Long, createItemDTO: RequestCreateItemDTO): CreateItemResultDTO

    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO

    fun getAllItems(pageable: Pageable): Page<FindItemDTO>

    fun getItemsByName(keyword: String, pageable: Pageable): Page<FindItemDTO>

    fun deleteOneItem(itemId: Long): DeleteItemResultDTO
}