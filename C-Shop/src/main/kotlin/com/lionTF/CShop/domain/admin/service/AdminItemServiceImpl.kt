package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@RequiredArgsConstructor
@Transactional
class AdminItemServiceImpl(

    private val adminItemRepository: AdminItemRepository,

): AdminItemService {

    // 상품 등록
    override fun createItem(createItemDTO: CreateItemDTO): CreateItemResultDTO {

        // 상품 존재 여부
        val existsItemName = adminItemRepository.existsByItemName(createItemDTO.itemName, true)

        if (existsItemName == null) {
            adminItemRepository.save(itemToItemDTO(createItemDTO))

            return setCreateSuccessItemResultDTO()
        } else {
            return setCreateFailItemResultDTO()
        }
    }


    // 상품 수정
    override fun updateItem(itemId: Long, createItemDTO: CreateItemDTO): CreateItemResultDTO {

        val existsItem = adminItemRepository.existsById(itemId)

        if (existsItem) {
            val item = adminItemRepository.findById(itemId).orElseThrow()
            item.update(createItemDTO)

            return setUpdateSuccessItemResultDTO()
        } else {
            return setUpdateFailItemResultDTO()
        }
    }

    // 상품 삭제
    override fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
        deleteItemDTO.itemIds.forEach{
            val findItemStatusById = adminItemRepository.findItemStatusById(it)

            if (findItemStatusById == null) {
                return setDeleteFailItemResultDTO()

            } else if (findItemStatusById!!) {
                val item = adminItemRepository.findById(it).orElseThrow()
                item.delete()

            } else {
                return setDeleteFailItemResultDTO()
            }
        }

        return setDeleteSuccessItemResultDTO()
    }
}