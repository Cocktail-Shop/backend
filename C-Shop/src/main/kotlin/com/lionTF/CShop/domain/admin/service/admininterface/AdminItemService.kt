package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*
import org.springframework.data.domain.Pageable

interface AdminItemService {

    fun createItem(requestCreateItemDTO: RequestCreateItemDTO): AdminResponseDTO

    fun updateItem(itemId: Long, requestCreateItemDTO: RequestCreateItemDTO): AdminResponseDTO

    fun getAllItems(pageable: Pageable): ResponseSearchItemSearchDTO

    fun getItemsByName(keyword: String, pageable: Pageable): ResponseSearchItemSearchDTO

    fun findItem(itemId: Long): ResponseItemDTO

    fun deleteOneItem(itemId: Long): AdminResponseDTO

    //    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO
}
