package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@RequiredArgsConstructor
class AdminItemServiceImpl(

    private val adminItemRepository: AdminItemRepository,

): AdminItemService {

    // 상품 등록
    @Transactional
    override fun createItem(createItemDTO: CreateItemDTO): createItemResultDTO {

        // 상품 존재 여부
        val existsItemName = adminItemRepository.existsByItemName(createItemDTO.itemName)

        if (existsItemName == null) {
            adminItemRepository.save(itemToItem(createItemDTO))

            return setCreateSuccessItemResultDTO()
        } else {
            return setCreateFailItemResultDTO()
        }
    }


    // 상품 수정
    @Transactional
    override fun updateItem(itemId: Long, createItemDTO: CreateItemDTO): createItemResultDTO {

        val existsItem = adminItemRepository.existsById(itemId)

        if (existsItem) {
            val item = adminItemRepository.findById(itemId).orElseThrow()
            item.update(createItemDTO)

            return setUpdateSuccessItemResultDTO()
        } else {
            return setUpdateFailItemResultDTO()
        }
    }
}