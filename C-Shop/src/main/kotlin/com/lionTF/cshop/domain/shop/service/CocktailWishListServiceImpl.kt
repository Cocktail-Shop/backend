package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.cshop.domain.shop.controller.dto.CocktailWishListDTO
import com.lionTF.cshop.domain.shop.models.CocktailWishList
import com.lionTF.cshop.domain.shop.repository.CocktailWishListRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.CocktailWishListService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CocktailWishListServiceImpl(

    private val adminCocktailRepository: AdminCocktailRepository,
    private val cocktailWishListRepository: CocktailWishListRepository

) : CocktailWishListService {

    @Transactional
    override fun createWishList(memberId: Long, cocktailId: Long): AdminResponseDTO {
        val cocktail = adminCocktailRepository.findCocktails(true, cocktailId)
        val cocktailWishList = cocktailWishListRepository.findCocktailWishList(memberId, cocktailId)

        return when {
            cocktail == null -> {
                AdminResponseDTO.toFailCreateWishListByNoContentItemId()
            }
            cocktailWishList != null-> {
                AdminResponseDTO.toFailCreateWishListByDuplicatedCocktail()
            }
            else -> {
                cocktailWishListRepository.save(CocktailWishList.toEntity(cocktail, memberId))

                AdminResponseDTO.toSuccessCreateCocktailWishList()
            }
        }
    }

    override fun getWishList(memberId: Long, pageable: Pageable): Page<CocktailWishListDTO> {
        return cocktailWishListRepository.findWishListByMemberId(memberId, pageable)
    }

    @Transactional
    override fun deleteWishList(memberId: Long, cocktailWishListId: Long): AdminResponseDTO {
        val isWishList = cocktailWishListRepository.existsById(cocktailWishListId)

        return if (isWishList) {
            cocktailWishListRepository.deleteById(cocktailWishListId)

            AdminResponseDTO.toSuccessDeleteCocktailWishList()
        } else {
            AdminResponseDTO.toFailDeleteWishListByNoContentWishListId()
        }
    }
}
