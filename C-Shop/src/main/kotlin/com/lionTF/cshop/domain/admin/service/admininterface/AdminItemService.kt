package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Item
import org.springframework.data.domain.Pageable

interface AdminItemService {

    fun createItem(requestCreateItemDTO: RequestCreateItemDTO, itemImgUrl: String?): AdminResponseDTO

    fun updateItem(itemId: Long, requestCreateItemDTO: RequestCreateItemDTO, itemImgUrl: String?): AdminResponseDTO

    fun getAllItems(pageable: Pageable): ResponseSearchItemSearchDTO

    fun getItemsByName(keyword: String, pageable: Pageable): ResponseSearchItemSearchDTO

    fun findItem(itemId: Long): ResponseItemDTO

    fun deleteOneItem(itemId: Long): AdminResponseDTO

    fun findItemById(itemId: Long): Item

    //    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO
}
