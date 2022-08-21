package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Item
import org.springframework.data.domain.Pageable

interface AdminItemService {

    fun createItem(requestCreateItemDTO: ItemCreateRequestDTO, itemImgUrl: String?): AdminResponseDTO

    fun updateItem(itemId: Long, requestCreateItemDTO: ItemCreateRequestDTO, itemImgUrl: String?): AdminResponseDTO

    fun getAllItems(pageable: Pageable): ItemsSearchDTO

    fun getItemsByName(itemName: String, pageable: Pageable): ItemsSearchDTO

    fun findItem(itemId: Long): ItemResponseDTO

    fun deleteOneItem(itemId: Long): AdminResponseDTO

    fun findItemById(itemId: Long): Item
}
