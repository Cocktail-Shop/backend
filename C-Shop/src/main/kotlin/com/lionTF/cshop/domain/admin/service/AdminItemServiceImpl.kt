package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.cshop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminItemService
import com.lionTF.cshop.domain.shop.repository.ItemWishListRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminItemServiceImpl(
    private val adminItemRepository: AdminItemRepository,
    private val adminCocktailItemRepository: AdminCocktailItemRepository,
    private val adminCocktailRepository: AdminCocktailRepository,
    private val itemWishListRepository: ItemWishListRepository,
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
            adminItemRepository.save(Item.fromItemCreateRequestDTO(requestCreateItemDTO, itemImgUrl))
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

            val cocktailList = adminCocktailItemRepository.findCocktailItemByItemId(itemId)

            if (cocktailList != null) {
                for (cocktail in cocktailList) {
                    val cocktailItemList = adminCocktailItemRepository.findCocktailItemByCocktail(cocktail)

                    if (cocktailItemList != null) {
                        for (cocktailItem in cocktailItemList) {
                            val countItem = adminItemRepository.countItemStatusIsFalse(cocktailItem.item.itemId)

                            if (countItem == cocktailItemList.size) {
                                cocktail.deleteCocktail()
                            }
                        }
                    }
                }
            }
            itemWishListRepository.deleteWishListByItemId(itemId)

            AdminResponseDTO.toSuccessDeleteItemResponseDTO()
        }
    }

    override fun findItemById(itemId: Long): Item {
        return adminItemRepository.getReferenceById(itemId)
    }

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

    override fun getItems(pageable: Pageable): ItemsSearchDTO {
        val items = adminItemRepository.findAllItems(pageable)

        return ItemsSearchDTO.toFormDTO(items)
    }

    override fun getItems(): List<ItemsDTO> {
        return adminItemRepository.getItems(true)
    }

    override fun getItemsByName(itemName: String, pageable: Pageable): ItemsSearchDTO {
        val items = adminItemRepository.findItemsByName(itemName, pageable)

        return ItemsSearchDTO.toFormDTO(items, itemName)
    }
}
