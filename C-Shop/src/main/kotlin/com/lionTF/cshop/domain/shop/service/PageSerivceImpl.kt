package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.models.*
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.repository.CocktailRepository
import com.lionTF.cshop.domain.shop.repository.ItemRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.PageSerivce
import com.querydsl.core.BooleanBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.function.Function

@Service
class PageSerivceImpl(
    private val itemRepository: ItemRepository,
    private val cocktailRepository: CocktailRepository,
    private val queryFactory: JPAQueryFactory,
) : PageSerivce{

    //전체 조회 페이지 처리
    override fun getPage(requestDTO: PageRequestDTO, sort: String, category: String) : Any {
        val pageable: Pageable =requestDTO.getPageable(Sort.by(sort).descending())
        if(category == Category.ALCOHOL.toString()){
            val result: Page<Item> = itemRepository.findItemByCategoryAndItemStatus(Category.ALCOHOL,true,pageable)
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> SearchItemInfoDTO.fromItem(Item)}
            return ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result,fn))
        }
        else if(category == Category.NONALCOHOL.toString()){
            val result: Page<Item> = itemRepository.findItemByCategoryAndItemStatus(Category.NONALCOHOL,true,pageable)
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> SearchItemInfoDTO.fromItem(Item) }
            return ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result,fn))
        }
        else{
            val result: Page<Cocktail> = cocktailRepository.findCocktailByCategoryAndCocktailStatus(Category.COCKTAIL,true,pageable)
            val fn: Function<Cocktail, SearchCocktailInfoDTO> = Function { Cocktail -> SearchCocktailInfoDTO.fromCocktail(Cocktail) }
            return CocktailPageResponseDTO.setCocktailPageResponseDTO(PageResultDTO(result,fn))
        }
    }

    //검색 조회 페이지 처리
    override fun getSearchPage(requestDTO: PageRequestDTO, sort: String, category: String, keyword: String) : Any {
        val pageable: Pageable =requestDTO.getPageable(Sort.by(sort).descending(),keyword)
        if(category == Category.ALCOHOL.toString()){
            val booleanBuilder:BooleanBuilder = BooleanBuilder()
            booleanBuilder.and(QItem.item.itemName.contains(keyword))
            booleanBuilder.and(QItem.item.category.eq(Category.ALCOHOL))
            val itemList = queryFactory.selectFrom(QItem.item).where(booleanBuilder).fetch()

            val start = pageable.offset.toInt()
            var end = start + pageable.pageSize
            if(end> itemList.size){
                end = itemList.size
            }
            else{
                end = start + pageable.pageSize
            }
            val result: Page<Item> = PageImpl(itemList.subList(start,end),pageable,itemList.size.toLong())
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> SearchItemInfoDTO.fromItem(Item)}
            return ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result,fn))
        }
        else if(category == Category.NONALCOHOL.toString()){
            val booleanBuilder:BooleanBuilder = BooleanBuilder()
            booleanBuilder.and(QItem.item.itemName.contains(keyword))
            booleanBuilder.and(QItem.item.category.eq(Category.NONALCOHOL))
            val itemList = queryFactory.selectFrom(QItem.item).where(booleanBuilder).fetch()
            val start = pageable.offset.toInt()
            var end = start + pageable.pageSize
            if(end> itemList.size){
                end = itemList.size
            }
            else{
                end = start + pageable.pageSize
            }
            val result: Page<Item> = PageImpl(itemList.subList(start,end),pageable,itemList.size.toLong())
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> SearchItemInfoDTO.fromItem(Item)}
            return ItemPageResponseDTO.setItemPageResponseDTO(PageResultDTO(result,fn))
        }
        else{
            val booleanBuilder: BooleanBuilder = BooleanBuilder()
            booleanBuilder.and(QCocktail.cocktail.cocktailName.contains(keyword))
            val cocktailList = queryFactory.selectFrom(QCocktail.cocktail).where(booleanBuilder).fetch()
            val start = pageable.offset.toInt()
            var end = start + pageable.pageSize
            if(end> cocktailList.size){
                end = cocktailList.size
            }
            else{
                end = start + pageable.pageSize
            }
            val result: Page<Cocktail> = PageImpl(cocktailList.subList(start,end),pageable,cocktailList.size.toLong())
            val fn: Function<Cocktail, SearchCocktailInfoDTO> = Function { Cocktail -> SearchCocktailInfoDTO.fromCocktail(Cocktail) }
            return CocktailPageResponseDTO.setCocktailPageResponseDTO(PageResultDTO(result,fn))
        }
    }
}