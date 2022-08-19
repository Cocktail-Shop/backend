package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.models.CocktailItem
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.cshop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminCocktailService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class AdminCocktailServiceImpl(
    private val adminCocktailRepository: AdminCocktailRepository,
    private val adminCocktailItemRepository: AdminCocktailItemRepository,
    private val adminItemRepository: AdminItemRepository,

    ) : AdminCocktailService {

    override fun getAllCocktail(pageable: Pageable): ResponseSearchCocktailSearchDTO {
        val cocktails = adminCocktailRepository.findAllCocktails(pageable)

        return ResponseSearchCocktailSearchDTO.cocktailToResponseCocktailSearchPageDTO(cocktails)
    }

    override fun getCocktailsByName(cocktailName: String, pageable: Pageable): ResponseSearchCocktailSearchDTO {
        val cocktails = adminCocktailRepository.findCocktailsByName(cocktailName, pageable)

        return ResponseSearchCocktailSearchDTO.cocktailToResponseCocktailSearchPageDTO(cocktails, cocktailName)
    }


    // 칵테일 단건 조회
    override fun findCocktail(cocktailId: Long, itemIds: MutableList<Long>): ResponseCocktailDTO {
        val cocktailExisted = adminCocktailRepository.existsById(cocktailId)

        return if (!cocktailExisted) {
            ResponseCocktailDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "존재하지 않는 칵테일입니다.",
                null
            )
        } else {
            val cocktailResultDTO = adminCocktailRepository.findCocktailById(cocktailId)
            return ResponseCocktailDTO.cocktailToResponseCocktailPageDTO(
                CocktailResultDTOAddItemIds.addItemIds(
                    cocktailResultDTO,
                    itemIds
                )
            )
        }
    }


    @Transactional
    override fun createCocktail(
        requestCreateCocktailDTO: RequestCreateCocktailDTO,
        cocktailImgUrl: String?
    ): AdminResponseDTO {
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return if (!formToExistedItems(requestCreateCocktailDTO.itemIds)) {
            AdminResponseDTO.noContentItem()

        } else if (requestCreateCocktailDTO.itemIds.isEmpty()) {
            AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.save(
                Cocktail.requestCreateCocktailDTOtoCocktail(
                    requestCreateCocktailDTO,
                    cocktailImgUrl
                )
            )

            requestCreateCocktailDTO.itemIds
                .asSequence()
                .map { adminItemRepository.getReferenceById(it) }
                .mapTo(cocktailItemList) { CocktailItem.requestCreateCocktailItemDTOtoCocktailItem(it, cocktail) }
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

            AdminResponseDTO.toSuccessDeleteCocktailResponseDTO()
        }
    }

    override fun findCocktailById(cocktailId: Long): Cocktail {
        return adminCocktailRepository.getReferenceById(cocktailId)
    }

    @Transactional
    override fun updateCocktail(
        requestCreateCocktailDTO: RequestCreateCocktailDTO,
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

            for (itemId in requestCreateCocktailDTO.itemIds) {
                val item = adminItemRepository.getReferenceById(itemId)
                val cocktailItem = CocktailItem.requestCreateCocktailItemDTOtoCocktailItem(item, cocktail)
                cocktailItemList.add(cocktailItem)
            }
            adminCocktailItemRepository.saveAll(cocktailItemList)

            AdminResponseDTO.toSuccessUpdateCocktailResponseDTO()
        }
    }


    // 존재하는 단일 상품인지 검사하는 함수
    private fun existedItem(itemId: Long): Item? {
        return adminItemRepository.findItem(itemId)
    }

    // Form으로부터 받아온 itemId들이 존재하는 상품인지 검사
    private fun formToExistedItems(itemList: MutableList<Long>): Boolean {
        return itemList.none { existedItem(it) == null}
    }


    // 존재하는 단일 상품인지 검사하는 함수
    private fun existedCocktail(cocktailId: Long): Cocktail? {
        return adminCocktailRepository.findCocktail(cocktailId)
    }


    // Form으로부터 받아온 cocktailId들이 존재하는 상품인지 검사
    private fun formToExistedCocktails(cocktailList: MutableList<Long>): Boolean {
        return cocktailList.none { existedCocktail(it) == null }
    }

    // 한개 이상의 칵테일 상품 삭제
//    override fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO {
//        return if (!formToExistedCocktails(deleteCocktailDTO.cocktailIds)) {
//            setDeleteFailCocktailResultDTO()
//        } else {
//            for (cocktailId in deleteCocktailDTO.cocktailIds) {
//                val cocktail = adminCocktailRepository.getReferenceById(cocktailId)
//                cocktail.deleteCocktail()
//            }
//
//            setDeleteSuccessCocktailResultDTO()
//        }
//    }
}
