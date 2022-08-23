package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminItemService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminItemServiceImpl(
    private val adminItemRepository: AdminItemRepository,
) : AdminItemService {

    @Transactional
    override fun createItem(requestCreateItemDTO: ItemCreateRequestDTO, itemImgUrl: String?): AdminResponseDTO {

        return if (requestCreateItemDTO.amount <= 0 && requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO()

        } else if (requestCreateItemDTO.amount <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO()

        } else if (requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO()

        } else {
            adminItemRepository.save(Item.toEntity(requestCreateItemDTO, itemImgUrl))
            AdminResponseDTO.toSuccessCreateItemResponseDTO()
        }
    }

    @Transactional
    override fun updateItem(
        itemId: Long,
        requestCreateItemDTO: ItemCreateRequestDTO,
        itemImgUrl: String?
    ): AdminResponseDTO {
        val itemExisted = adminItemRepository.existsById(itemId)

        return if (!itemExisted) {
            AdminResponseDTO.toFailUpdateItemResponseDTO()

        } else if (requestCreateItemDTO.amount <= 0 && requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO()

        } else if (requestCreateItemDTO.amount <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO()

        } else if (requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO()

        } else {
            adminItemRepository.getReferenceById(itemId).update(requestCreateItemDTO, itemImgUrl)

            AdminResponseDTO.toSuccessUpdateItemResponseDTO()
        }
    }


    @Transactional
    override fun deleteOneItem(itemId: Long): AdminResponseDTO {
        val itemExisted = adminItemRepository.existsById(itemId)

        return if (!itemExisted) {
            AdminResponseDTO.toFailDeleteItemResponseDTO()
        } else {
            val item = adminItemRepository.getReferenceById(itemId)
            item.delete()

            AdminResponseDTO.toSuccessDeleteItemResponseDTO()
        }
    }

    override fun findItemById(itemId: Long): Item {
        return adminItemRepository.getReferenceById(itemId)
    }

    // 상품 단건 조회
    override fun findItem(itemId: Long): ItemResponseDTO {
        val itemExisted = adminItemRepository.existsById(itemId)

        return if (!itemExisted) {
            ItemResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "존재 하지 않는 상품입니다."
            )
        } else {
            val itemResultDTO = adminItemRepository.findItemById(itemId)
            return ItemResponseDTO.toFormDTO(itemResultDTO)
        }

    }

    // 상품 전체 조회
    override fun getAllItems(pageable: Pageable): ItemsSearchDTO {
        val items = adminItemRepository.findAllItems(pageable)

        return ItemsSearchDTO.toFormDTO(items)
    }

    // 상품 이름으로 조회
    override fun getItemsByName(itemName: String, pageable: Pageable): ItemsSearchDTO {
        val items = adminItemRepository.findItemsByName(itemName, pageable)

        return ItemsSearchDTO.toFormDTO(items, itemName)
    }
}
