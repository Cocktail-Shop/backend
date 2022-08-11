package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AdminItemServiceImpl(

    private val adminItemRepository: AdminItemRepository,

): AdminItemService {

    // 상품 등록
    override fun createItem(requestCreateItemDTO: RequestCreateItemDTO): AdminResponseDTO {
        // 상품 존재 여부
        val existsItemName = adminItemRepository.existsByItemName(requestCreateItemDTO.itemName, true, requestCreateItemDTO.degree)

        return if (existsItemName != null) {
            AdminResponseDTO.toFailCreateItemResponseDTO()
        }
        else if (requestCreateItemDTO.amount <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO()
        }
        else if (requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO()
        }
        else {
            adminItemRepository.save(Item.requestCreateItemDTOtoItem(requestCreateItemDTO))
            AdminResponseDTO.toSuccessCreateItemResponseDTO()
        }
    }


    // 상품 수정
    override fun updateItem(itemId: Long, requestCreateItemDTO: RequestCreateItemDTO): Any {
        val existsItem = adminItemRepository.existsById(itemId)

        return if (!existsItem) {
            AdminResponseDTO.toFailUpdateItemResponseDTO()
        }
        else if (requestCreateItemDTO.amount <= 0 && requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO()
        }
        else if (requestCreateItemDTO.amount <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO()
        }
        else if (requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO()
        }
        else {
            adminItemRepository.getReferenceById(itemId).update(requestCreateItemDTO)

            AdminResponseDTO.toSuccessUpdateItemResponseDTO()
        }
    }

    // 상품 삭제
    override fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
        deleteItemDTO.itemIds.forEach{
            val findItemStatusById = adminItemRepository.findItemStatusById(it)

            if (findItemStatusById == null) {
                return setDeleteFailItemResultDTO()
            }
            else if (findItemStatusById) {
                val item = adminItemRepository.findById(it).orElseThrow()
                item.delete()
            }
            else {
                return setDeleteFailItemResultDTO()
            }
        }

        return setDeleteSuccessItemResultDTO()
    }

    // 하나의 상품 삭제
    override fun deleteOneItem(itemId: Long): DeleteItemResultDTO {
        val item = adminItemRepository.getReferenceById(itemId)

        item.delete()
        return setDeleteSuccessItemResultDTO()
    }

    // 상품 단건 조회
    override fun findItem(itemId: Long): ResponseItemDTO {
        val existsItem = adminItemRepository.existsById(itemId)

        return if (!existsItem) {
            ResponseItemDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "존재 하지 않는 상품입니다.",
                null
            )
        } else {
            val itemResultDTO = adminItemRepository.findItemById(itemId)
            return ResponseItemDTO.itemToResponseItemPageDTO(itemResultDTO)
        }

    }

    // 상품 전체 조회
    override fun getAllItems(pageable: Pageable): Page<FindItemDTO> {
        return adminItemRepository.findAllItems(pageable)
    }

    override fun getItemsByName(keyword: String, pageable: Pageable): Page<FindItemDTO> {
        return adminItemRepository.findItemsByName(keyword, pageable)
    }
}