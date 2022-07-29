package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AdminItemServiceImpl(

    private val adminItemRepository: AdminItemRepository,

): AdminItemService {

    override fun createItem(createItemDTO: createItemDTO): createItemResultDTO {

        // 상품 존재 여부
        val existsItemName = adminItemRepository.existsByItemName(createItemDTO.itemName)

        if (existsItemName == null) {
            adminItemRepository.save(itemToItem(createItemDTO))

            return setCreateSuccessItemResultDTO()
        } else {
            return setCreateFailItemResultDTO()
        }
    }
}