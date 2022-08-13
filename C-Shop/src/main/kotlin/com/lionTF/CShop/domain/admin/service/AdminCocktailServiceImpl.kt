package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.CShop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AdminCocktailServiceImpl(
    private val adminCocktailRepository: AdminCocktailRepository,
    private val adminCocktailItemRepository: AdminCocktailItemRepository,
    private val adminItemRepository: AdminItemRepository,

): AdminCocktailService{

    // 전체 칵테일 조회
    override fun getAllCocktail(pageable: Pageable): ResponseSearchCocktailSearchDTO {
        val findAllCocktails = adminCocktailRepository.findAllCocktails(pageable)

        return ResponseSearchCocktailSearchDTO.cocktailToResponseCocktailSearchPageDTO(findAllCocktails, "")
    }

    // 칵테일 상품명으로 칵테일 조회
    override fun getCocktailsByName(keyword: String, pageable: Pageable): ResponseSearchCocktailSearchDTO {
        val findCocktailsByName = adminCocktailRepository.findCocktailsByName(keyword, pageable)

        return ResponseSearchCocktailSearchDTO.cocktailToResponseCocktailSearchPageDTO(findCocktailsByName, keyword)
    }


    // 칵테일 단건 조회
    override fun findCocktail(cocktailId: Long, itemIds: MutableList<Long>): ResponseCocktailDTO {
        val existsCocktail = adminCocktailRepository.existsById(cocktailId)

        return if (!existsCocktail) {
            ResponseCocktailDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "존재하지 않는 칵테일입니다.",
                null
            )
        } else {
            val cocktailResultDTO = adminCocktailRepository.findCocktailById(cocktailId)
            return ResponseCocktailDTO.cocktailToResponseCocktailPageDTO(CocktailResultDTOAddItemIds.addItemIds(cocktailResultDTO, itemIds))
        }
    }


    // 칵테일 상품 등록
    override fun createCocktail(requestCreateCocktailDTO: RequestCreateCocktailDTO): AdminResponseDTO {
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return if (!formToExistedItems(requestCreateCocktailDTO.itemIds)) {
            AdminResponseDTO.noContentItem()

        } else if (requestCreateCocktailDTO.itemIds.isEmpty()) {
            AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.save(Cocktail.requestCreateCocktailDTOtoCocktail(requestCreateCocktailDTO))

            for (itemId in requestCreateCocktailDTO.itemIds) {
                val item = adminItemRepository.getReferenceById(itemId)

                val cocktailItem = CocktailItem.requestCreateCocktailItemDTOtoCocktailItem(item, cocktail)
                cocktailItemList.add(cocktailItem)
            }
            adminCocktailItemRepository.saveAll(cocktailItemList)

            AdminResponseDTO.toSuccessCreateCocktailResponseDTO()
        }
    }


    // 한개의 칵테일 삭제
    override fun deleteOneCocktail(cocktailId: Long): AdminResponseDTO {
        val existsCocktail = adminCocktailRepository.existsById(cocktailId)

        return if (!existsCocktail) {
            AdminResponseDTO.toFailDeleteCocktailResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.getReferenceById(cocktailId)
            cocktail.deleteCocktail()

            AdminResponseDTO.toSuccessDeleteCocktailResponseDTO()
        }
    }

    // 칵테일 상품 수정
    override fun updateCocktail(
        requestCreateCocktailDTO: RequestCreateCocktailDTO,
        cocktailId: Long,
        itemIds: MutableList<Long>
    ): AdminResponseDTO {

        val existsCocktail = adminCocktailRepository.existsById(cocktailId)

        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return if (!existsCocktail || !(formToExistedItems(itemIds))) {
            AdminResponseDTO.noContentItem()

        }else if(itemIds.isEmpty()) {
            AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO()

        } else {
            val cocktail = adminCocktailRepository.getReferenceById(cocktailId)

            cocktail.updateCocktail(requestCreateCocktailDTO)

            val findAllByCocktailId = adminCocktailItemRepository.findAllByCocktailId(cocktailId)
            adminCocktailItemRepository.deleteAll(findAllByCocktailId)

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
    private fun existedItem(itemId: Long): Optional<Item> {
        return adminItemRepository.findById(itemId)
    }

    // Form으로부터 받아온 itemId들이 존재하는 상품인지 검사
    private fun formToExistedItems(itemList: MutableList<Long>): Boolean {
        for (itemId in itemList) {
            when (existedItem(itemId).isEmpty) {
                true -> {return false}
            }
        }
        return true
    }


    // 존재하는 단일 상품인지 검사하는 함수
    private fun existedCocktail(cocktailId: Long): Optional<Cocktail> {
        return adminCocktailRepository.findById(cocktailId)
    }


    // Form으로부터 받아온 cocktailId들이 존재하는 상품인지 검사
    private fun formToExistedCocktails(cocktailList: MutableList<Long>): Boolean {
        for (cocktailId in cocktailList) {
            when (existedCocktail(cocktailId).isEmpty) {
                true -> {return false}
            }
        }
        return true
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
