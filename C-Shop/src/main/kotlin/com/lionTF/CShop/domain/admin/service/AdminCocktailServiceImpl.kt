package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.CShop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
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

    // 칵테일 상품 등록
    override fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO {
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return when (existedCocktailByCocktailName(createCocktailDTO)) {
            true -> when (formToExistedItems(createCocktailDTO.itemIds)) {
                true -> {
                    val cocktail = adminCocktailRepository.save(cocktailToCockTailDTO(createCocktailDTO))

                    for (itemId in createCocktailDTO.itemIds) {
                        val item = adminItemRepository.getReferenceById(itemId)
                        val cocktailItem = cocktailItemToCocktailItemDTO(item, cocktail)
                        cocktailItemList.add(cocktailItem)
                    }
                    adminCocktailItemRepository.saveAll(cocktailItemList)

                    return setCreateSuccessCocktailResultDTO(cocktail.cocktailId)
                }
                else -> failToNoContentItemResultDTO()
            }
            else -> setCreateFailCocktailResultDTO()
        }
    }

    // 칵테일 상품 삭제
    override fun deleteCocktail(deleteCocktailDTO: DeleteCocktailDTO): DeleteCocktailResultDTO {

        return when (formToExistedCocktails(deleteCocktailDTO.cocktailIds)){
            true -> {
                for (cocktailId in deleteCocktailDTO.cocktailIds) {
                    val cocktail = adminCocktailRepository.getReferenceById(cocktailId)
                    cocktail.deleteCocktail()
                }

                return setDeleteSuccessCocktailResultDTO()
            }
            else -> setDeleteFailCocktailResultDTO()
        }
    }

    // 칵테일 상품 수정
    override fun updateCocktail(createCocktailDTO: CreateCocktailDTO, cocktailId: Long): CreateCocktailResultDTO {

        val existsCocktail = adminCocktailRepository.existsById(cocktailId)

        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        return when (existsCocktail) {
                         // form으로부터 받아온 cocktailI가 존재하는지 여부
            true -> when (formToExistedItems(createCocktailDTO.itemIds)) {
                true -> {
                    val cocktail = adminCocktailRepository.getReferenceById(cocktailId)

                    cocktail.updateCocktail(createCocktailDTO)

                    val findAllByCocktailId = adminCocktailItemRepository.findAllByCocktailId(cocktailId)
                    adminCocktailItemRepository.deleteAll(findAllByCocktailId)

                    for (itemId in createCocktailDTO.itemIds) {
                        val item = adminItemRepository.getReferenceById(itemId)
                        val cocktailItem = cocktailItemToCocktailItemDTO(item, cocktail)
                        cocktailItemList.add(cocktailItem)
                    }

                    adminCocktailItemRepository.saveAll(cocktailItemList)

                    return setUpdateSuccessCocktailResultDTO(cocktail.cocktailId)
                }
                else -> failToNoContentItemResultDTO()
            }
            false -> setUpdateFailCocktailResultDTO()
        }
    }


    // 이미 등록된 칵테일인지 검사하는 함수
    private fun existedCocktailByCocktailName(createCocktailDTO: CreateCocktailDTO): Boolean {
        val existsByCocktailName = adminCocktailRepository.existsByCocktailName(createCocktailDTO.cocktailName, true)

        return when (existsByCocktailName) {
            null ->  true
            else ->  false
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
}
