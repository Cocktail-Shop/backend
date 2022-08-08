package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Cocktail
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.repository.CocktailRepository
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.function.Function

@Service
class PageSerivce(
    private val itemRepository: ItemRepository,
    private val cocktailRepository: CocktailRepository,
) {

    fun getPage(requestDTO: PageRequestDTO, sort: String, category: String) : Any {
        val pageable: Pageable =requestDTO.getPageable(Sort.by(sort).descending())
        if(category == Category.ALCOHOL.toString()){
            val result: Page<Item> = itemRepository.findItemByCategoryAndItemStatus(Category.ALCOHOL,true,pageable)
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> itemToSearchIteminfoDTO(Item)}
            return setItemPageResponseDTO(PageResultDTO(result,fn))
        }
        else if(category == Category.NONALCOHOL.toString()){
            val result: Page<Item> = itemRepository.findItemByCategoryAndItemStatus(Category.NONALCOHOL,true,pageable)
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> itemToSearchIteminfoDTO(Item) }
            return setItemPageResponseDTO(PageResultDTO(result,fn))
        }
        else{
            val result: Page<Cocktail> = cocktailRepository.findCocktailByCategoryAndCocktailStatus(Category.COCKTAIL,true,pageable)
            val fn: Function<Cocktail, SearchCocktailInfoDTO> = Function { Cocktail -> cocktailToSearchCocktailInfoDTO(Cocktail) }
            return setCocktailPageResponseDTO(PageResultDTO(result,fn))
        }
    }
}