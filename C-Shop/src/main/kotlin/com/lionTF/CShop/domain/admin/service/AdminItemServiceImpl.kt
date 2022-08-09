package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors.*
import javax.transaction.Transactional

@Service
@Transactional
class AdminItemServiceImpl(

    private val adminItemRepository: AdminItemRepository,

): AdminItemService {

    // 상품 등록
    override fun createItem(createItemDTO: ItemDTO): CreateItemResultDTO {
        // 상품 존재 여부
        val existsItemName = adminItemRepository.existsByItemName(createItemDTO.itemName, true, createItemDTO.degree)

        return if (existsItemName == null) {
            adminItemRepository.save(itemDTOToItem(createItemDTO))
            setCreateSuccessItemResultDTO()
        } else {
            setCreateFailItemResultDTO()
        }
    }


    // 상품 수정
    override fun updateItem(itemId: Long, createItemDTO: ItemDTO): CreateItemResultDTO {
        val existsItem = adminItemRepository.existsById(itemId)

        return if (existsItem) {
            val item = adminItemRepository.findById(itemId).orElseThrow()
            item.update(createItemDTO)

            setUpdateSuccessItemResultDTO()
        } else {
            setUpdateFailItemResultDTO()
        }
    }

    // 상품 삭제
    override fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
        deleteItemDTO.itemIds.forEach{
            val findItemStatusById = adminItemRepository.findItemStatusById(it)

            when {
                findItemStatusById == null -> {
                    return setDeleteFailItemResultDTO()
                }
                findItemStatusById -> {
                    val item = adminItemRepository.findById(it).orElseThrow()
                    item.delete()
                }
                else -> {
                    return setDeleteFailItemResultDTO()
                }
            }
        }

        return setDeleteSuccessItemResultDTO()
    }

    // 상품 전체 조회
    override fun getAllItems(pageable: Pageable): Page<FindItemDTO> {
        return adminItemRepository.findAllItems(pageable)
    }

    override fun getItemsByName(keyword: String, pageable: Pageable): Page<FindItemDTO> {
        return adminItemRepository.findItemsByName(keyword, pageable)
    }
}