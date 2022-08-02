package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.CocktailItem
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.CShop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.util.*
import kotlin.math.ceil

@Service
@RequiredArgsConstructor
class AdminCocktailServiceImpl(
    private val adminCocktailRepository: AdminCocktailRepository,
    private val adminCocktailItemRepository: AdminCocktailItemRepository,
    private val adminItemRepository: AdminItemRepository,

): AdminCocktailService{

    // 칵테일 상품 등록
    override fun createCocktail(createCocktailDTO: CreateCocktailDTO): CreateCocktailResultDTO {
        val cocktailItemList: MutableList<CocktailItem> = mutableListOf()

        if (existedCocktail(createCocktailDTO).isNullOrEmpty()) {
            if (formToExistedItems(createCocktailDTO)) {
                val cocktail = adminCocktailRepository.save(cocktailToCockTailDTO(createCocktailDTO))

                for (itemId in createCocktailDTO.itemIds) {
                    val item = adminItemRepository.getReferenceById(itemId)
                    val cocktailItem = cocktailItemToCocktailItemDTO(item, cocktail)
                    cocktailItemList.add(cocktailItem)

                }
            } else {
                return FailToNoContentItemResultDTO()
            }

            adminCocktailItemRepository.saveAll(cocktailItemList)

            return setCreateSuccessCocktailResultDTO()
        } else {
            return setCreateFailCocktailResultDTO()
        }
    }

    // 이미 등록된 칵테일인지 검사하는 함수
    fun existedCocktail(createCocktailDTO: CreateCocktailDTO): String? {
        return adminCocktailRepository.existsByCocktailName(createCocktailDTO.cocktailName, true)
    }

    // 존재하는 단일 상품인지 검사하는 함수
    fun existedItem(itemId: Long): Optional<Item> {
        return adminItemRepository.findById(itemId)
    }

    // Form으로부터 받아온 itemId들이 존재하는 상품인지 검사
    fun formToExistedItems(createCocktailDTO: CreateCocktailDTO): Boolean {
        for (itemId in createCocktailDTO.itemIds) {
            when (existedItem(itemId).isEmpty) {
                true -> {return false}
            }
        }
        return true
    }

}