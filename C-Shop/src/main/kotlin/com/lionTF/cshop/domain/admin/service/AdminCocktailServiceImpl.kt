package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.models.CocktailItem
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.cshop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminCocktailService
import com.lionTF.cshop.domain.shop.repository.CocktailWishListRepository
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminCocktailServiceImpl(
    private val adminCocktailRepository: AdminCocktailRepository,
    private val adminCocktailItemRepository: AdminCocktailItemRepository,
    private val adminItemRepository: AdminItemRepository,
    private val cocktailWishListRepository: CocktailWishListRepository,
) : AdminCocktailService {

    override fun getAllCocktail(pageable: Pageable): CocktailsSearchDTO {
        val cocktails = adminCocktailRepository.findAllCocktails(pageable)

        return CocktailsSearchDTO.toFormDTO(cocktails)
    }

    override fun getCocktailsByName(cocktailName: String, pageable: Pageable): CocktailsSearchDTO {
        val cocktails = adminCocktailRepository.findCocktailsByName(cocktailName, pageable)

        return CocktailsSearchDTO.toFormDTO(cocktails, cocktailName)
    }


    override fun findCocktail(cocktailId: Long, itemIds: MutableList<Long>): CocktailResponseDTO {
        val cocktailExisted = adminCocktailRepository.existsById(cocktailId)

        return if (!cocktailExisted) {
            CocktailResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "존재하지 않는 칵테일입니다."
            )
        } else {
            val cocktailResultDTO = adminCocktailRepository.findCocktailById(cocktailId)
            return CocktailResponseDTO.toFormDTO(
                CocktailResultDTOAddItemIds.addItemIds(
                    cocktailResultDTO,
                    itemIds
                )
            )
        }
    }


    @Transactional
    override fun createCocktail(
        requestCreateCocktailDTO: CocktailCreateRequestDTO,
        cocktailImgUrl: String?
    ): AdminResponseDTO {
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return if (!formToExistedItems(requestCreateCocktailDTO.itemIds)) {
            AdminResponseDTO.noContentItem()

        } else if (requestCreateCocktailDTO.itemIds.isEmpty()) {
            AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.save(
                Cocktail.fromCocktailCreateRequestDTO(
                    requestCreateCocktailDTO,
                    cocktailImgUrl
                )
            )

            requestCreateCocktailDTO.itemIds
                .asSequence()
                .map { adminItemRepository.getReferenceById(it) }
                .mapTo(cocktailItemList) { CocktailItem.fromRequestItemAndCocktail(it, cocktail) }
            adminCocktailItemRepository.saveAll(cocktailItemList)

            AdminResponseDTO.toSuccessCreateCocktailResponseDTO()
        }
    }


    @Transactional
    override fun deleteOneCocktail(cocktailId: Long): AdminResponseDTO {
        val cocktailExisted = adminCocktailRepository.existsById(cocktailId)

        return if (!cocktailExisted) {
            AdminResponseDTO.toFailDeleteCocktailResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.getReferenceById(cocktailId)
            cocktail.deleteCocktail()

            cocktailWishListRepository.deleteWishListByCocktailId(cocktailId)

            AdminResponseDTO.toSuccessDeleteCocktailResponseDTO()
        }
    }

    override fun findCocktailById(cocktailId: Long): Cocktail {
        return adminCocktailRepository.getReferenceById(cocktailId)
    }

    @Transactional
    override fun updateCocktail(
        requestCreateCocktailDTO: CocktailCreateRequestDTO,
        cocktailId: Long,
        itemIds: MutableList<Long>,
        cocktailImgUrl: String?
    ): AdminResponseDTO {

        val cocktailExisted = adminCocktailRepository.existsById(cocktailId)

        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return if (!cocktailExisted || !(formToExistedItems(itemIds))) {
            AdminResponseDTO.noContentItem()

        } else if (itemIds.isEmpty()) {
            AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.getReferenceById(cocktailId)

            cocktail.updateCocktail(requestCreateCocktailDTO, cocktailImgUrl)

            val cocktailItems = adminCocktailItemRepository.findAllByCocktailId(cocktailId)
            adminCocktailItemRepository.deleteAll(cocktailItems)

            requestCreateCocktailDTO.itemIds
                .asSequence()
                .map { adminItemRepository.getReferenceById(it) }
                .mapTo(cocktailItemList) { CocktailItem.fromRequestItemAndCocktail(it, cocktail) }
            adminCocktailItemRepository.saveAll(cocktailItemList)

            AdminResponseDTO.toSuccessUpdateCocktailResponseDTO()
        }
    }


    // 존재하는 단일 상품인지 검사하는 함수
    private fun existedItem(itemId: Long): Item? {
        return adminItemRepository.findItem(itemId, true)
    }

    // Form 으로부터 받아온 itemId 들이 존재하는 상품인지 검사
    private fun formToExistedItems(itemList: MutableList<Long>): Boolean {
        return itemList.none {
            existedItem(it) == null
        }
    }

    // 존재하는 단일 상품인지 검사하는 함수
    private fun existedCocktail(cocktailId: Long): Cocktail? {
        return adminCocktailRepository.findCocktail(cocktailId)
    }

    // Form 으로부터 받아온 cocktailId 들이 존재하는 상품인지 검사
    private fun formToExistedCocktails(cocktailList: MutableList<Long>): Boolean {
        return cocktailList.none {
            existedCocktail(it) == null
        }
    }
}
