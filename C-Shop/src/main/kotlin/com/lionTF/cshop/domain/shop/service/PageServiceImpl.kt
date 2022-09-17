package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.models.*
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.repository.CocktailRepository
import com.lionTF.cshop.domain.shop.repository.ItemRepository
import com.lionTF.cshop.domain.shop.repository.custom.SearchRepositoryCustom
import com.lionTF.cshop.domain.shop.service.shopinterface.PageService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.function.Function

@Service
class PageServiceImpl(
    private val itemRepository: ItemRepository,
    private val cocktailRepository: CocktailRepository,
    private val searchRepository: SearchRepositoryCustom,
) : PageService {

    //전체 조회 페이지 처리
    override fun getPage(requestDTO: PageRequestDTO, sort: String, category: String): Any {
        val pageable: Pageable = requestDTO.getPageable(Sort.by(sort).ascending())

        return when (category) {
            Category.ALCOHOL.toString() -> {
                val result: Page<Item> =
                    itemRepository.findItemByCategoryAndItemStatus(Category.ALCOHOL, true, pageable)

                val fn: Function<Item, SearchItemInfoDTO> = Function { Item ->
                    SearchItemInfoDTO.fromItem(Item)
                }

                ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result, fn))
            }
            Category.NONALCOHOL.toString() -> {
                val result: Page<Item> =
                    itemRepository.findItemByCategoryAndItemStatus(Category.NONALCOHOL, true, pageable)

                val fn: Function<Item, SearchItemInfoDTO> = Function { Item ->
                    SearchItemInfoDTO.fromItem(Item)
                }

                ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result, fn))
            }
            else -> {
                val result: Page<Cocktail> =
                    cocktailRepository.findCocktailByCategoryAndCocktailStatus(Category.COCKTAIL, true, pageable)

                val fn: Function<Cocktail, SearchCocktailInfoDTO> = Function { Cocktail ->
                    SearchCocktailInfoDTO.fromCocktail(Cocktail)
                }

                CocktailPageResponseDTO.setCocktailPageResponseDTO(PageResultDTO(result, fn))
            }
        }
    }

    //검색 조회 페이지 처리
    override fun getSearchPage(requestDTO: PageRequestDTO, sort: String, category: String, keyword: String): Any {
        val pageable: Pageable = requestDTO.getPageable(Sort.by(sort).ascending(), keyword)
        val start = pageable.offset.toInt()
        var end = start + pageable.pageSize
        when (category) {
            Category.ALCOHOL.toString() -> {
                val itemList = searchRepository.findAlcoholList(keyword)
                end = setEndPage(end, itemList, start, pageable)
                val result: Page<Item> = PageImpl(itemList.subList(start, end), pageable, itemList.size.toLong())
                val fn: Function<Item, SearchItemInfoDTO> = Function { Item ->
                    SearchItemInfoDTO.fromItem(Item)
                }

                return ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result, fn))
            }
            Category.NONALCOHOL.toString() -> {
                val itemList = searchRepository.findNonAlcoholList(keyword)
                end = setEndPage(end, itemList, start, pageable)//List에서 offset
                val result: Page<Item> = PageImpl(itemList.subList(start, end), pageable, itemList.size.toLong())

                val fn: Function<Item, SearchItemInfoDTO> = Function { Item ->
                    SearchItemInfoDTO.fromItem(Item)
                }

                return ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result, fn))
            }
            else -> {
                val cocktailList = searchRepository.findCocktailList(keyword)

                end = if (end > cocktailList.size) {
                    cocktailList.size
                } else {
                    start + pageable.pageSize
                }

                val result: Page<Cocktail> =
                    PageImpl(cocktailList.subList(start, end), pageable, cocktailList.size.toLong())

                val fn: Function<Cocktail, SearchCocktailInfoDTO> = Function { Cocktail ->
                    SearchCocktailInfoDTO.fromCocktail(Cocktail)
                }

                return CocktailPageResponseDTO.setCocktailPageResponseDTO(PageResultDTO(result, fn))
            }
        }
    }

    private fun setEndPage(
        end: Int,
        itemList: List<Item>,
        start: Int,
        pageable: Pageable
    ): Int {
        return if (end > itemList.size) {
            itemList.size
        } else {
            start + pageable.pageSize//pageSize 한페이지에 몇개 들어가는지
        }
    }
}
