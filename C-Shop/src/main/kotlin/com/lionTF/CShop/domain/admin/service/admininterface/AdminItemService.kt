package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.*
import org.springframework.data.domain.Pageable

interface AdminItemService {

    fun createItem(requestCreateItemDTO: RequestCreateItemDTO): AdminResponseDTO

    fun updateItem(itemId: Long, requestCreateItemDTO: RequestCreateItemDTO): Any

//    fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO

    fun getAllItems(pageable: Pageable): ResponseSearchItemSearchDTO

    fun getItemsByName(keyword: String, pageable: Pageable): ResponseSearchItemSearchDTO

    fun deleteOneItem(itemId: Long): AdminResponseDTO

    fun findItem(itemId: Long): ResponseItemDTO
}