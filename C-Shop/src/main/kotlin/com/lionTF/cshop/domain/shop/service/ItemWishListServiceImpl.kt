package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.shop.controller.dto.ItemWishListDTO
import com.lionTF.cshop.domain.shop.models.ItemWishList
import com.lionTF.cshop.domain.shop.repository.ItemWishListRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.ItemWishListService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ItemWishListServiceImpl(

    private val adminItemRepository: AdminItemRepository,
    private val itemWishListRepository: ItemWishListRepository

) : ItemWishListService {

    @Transactional
    override fun createWishList(memberId: Long, itemId: Long): AdminResponseDTO {
        val item = adminItemRepository.findItem(itemId, true)
        val itemWishList = itemWishListRepository.findItemWishList(memberId, itemId)

        return when {
            item == null -> {
                AdminResponseDTO.toFailCreateWishListByNoContentItemId()
            }
            itemWishList != null-> {
                AdminResponseDTO.toFailCreateWishListByDuplicatedItem()
            }
            else -> {
                itemWishListRepository.save(ItemWishList.toEntity(item, memberId))

                AdminResponseDTO.toSuccessCreateItemWishList()
            }
        }
    }

    override fun getWishList(memberId: Long, pageable: Pageable): Page<ItemWishListDTO> {
        return itemWishListRepository.findWishListByMemberId(memberId, pageable)
    }

    @Transactional
    override fun deleteWishList(memberId: Long, itemWishListId: Long): AdminResponseDTO {
        val isWishList = itemWishListRepository.existsById(itemWishListId)

        return if (isWishList) {
            itemWishListRepository.deleteById(itemWishListId)

            AdminResponseDTO.toSuccessDeleteItemWishList()
        } else {
            AdminResponseDTO.toFailDeleteWishListByNoContentWishListId()
        }
    }
}
