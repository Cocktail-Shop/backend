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
    override fun createItem(requestCreateItemDTO: RequestCreateItemDTO, itemImgUrl: String?): AdminResponseDTO {

        return if (requestCreateItemDTO.amount <= 0 && requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceAndAmountResponseDTO()

        } else if (requestCreateItemDTO.amount <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatAmountResponseDTO()

        } else if (requestCreateItemDTO.price <= 0) {
            AdminResponseDTO.toFailCreateItemByInvalidFormatPriceResponseDTO()

        } else {
            adminItemRepository.save(Item.requestCreateItemDTOtoItem(requestCreateItemDTO, itemImgUrl))
            AdminResponseDTO.toSuccessCreateItemResponseDTO()
        }
    }

    @Transactional
    override fun updateItem(
        itemId: Long,
        requestCreateItemDTO: RequestCreateItemDTO,
        itemImgUrl: String?
    ): AdminResponseDTO {
        val existsItem = adminItemRepository.existsById(itemId)

        return if (!existsItem) {
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
        val existsItem = adminItemRepository.existsById(itemId)

        return if (!existsItem) {
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
    override fun getAllItems(pageable: Pageable): ResponseSearchItemSearchDTO {
        val findAllItems = adminItemRepository.findAllItems(pageable)

        return ResponseSearchItemSearchDTO.itemToResponseItemSearchPageDTO(findAllItems, "")
    }

    // 상품 이름으로 조회
    override fun getItemsByName(keyword: String, pageable: Pageable): ResponseSearchItemSearchDTO {
        val findItemsByItemName = adminItemRepository.findItemsByName(keyword, pageable)

        return ResponseSearchItemSearchDTO.itemToResponseItemSearchPageDTO(findItemsByItemName, keyword)
    }

    // 상품 삭제
//    override fun deleteItems(deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
//        deleteItemDTO.itemIds.forEach{
//            val findItemStatusById = adminItemRepository.findItemStatusById(it)
//
//            if (findItemStatusById == null) {
//                return setDeleteFailItemResultDTO()
//            }
//            else if (findItemStatusById) {
//                val item = adminItemRepository.findById(it).orElseThrow()
//                item.delete()
//            }
//            else {
//                return setDeleteFailItemResultDTO()
//            }
//        }
//
//        return setDeleteSuccessItemResultDTO()
//    }
}
