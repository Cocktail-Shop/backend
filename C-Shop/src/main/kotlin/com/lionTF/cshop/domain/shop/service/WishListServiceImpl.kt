package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.shop.controller.dto.WishListDTO
import com.lionTF.cshop.domain.shop.models.WishList
import com.lionTF.cshop.domain.shop.repository.WishListRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.WishListService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WishListServiceImpl(

    private val adminItemRepository: AdminItemRepository,
    private val wishListRepository: WishListRepository

) : WishListService {

    @Transactional
    override fun createWishList(memberId: Long, itemId: Long): AdminResponseDTO {
        val item = adminItemRepository.findItem(itemId, true)

        return if (item == null) {
            AdminResponseDTO.toFailCreateWishListByNoContentItemId()
        } else {
            wishListRepository.save(WishList.toEntity(item, memberId))

            AdminResponseDTO.toSuccessCreateWishList()
        }
    }

    override fun getWishList(memberId: Long, pageable: Pageable): Page<WishListDTO> {
        return wishListRepository.findWishListByMemberId(true, memberId, pageable)
    }

    @Transactional
    override fun deleteWishList(memberId: Long, wishListId: Long): AdminResponseDTO {
        val isWishList = wishListRepository.existsById(wishListId)

        return if (isWishList) {
            val wishList = wishListRepository.getReferenceById(wishListId)
            wishList.delete()

            AdminResponseDTO.toSuccessDeleteWishList()
        } else {
            AdminResponseDTO.toFailDeleteWishListByNoContentWishListId()
        }
    }
}
