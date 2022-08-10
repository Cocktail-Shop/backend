package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.models.*
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.repository.CocktailRepository
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.QueryFactory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.function.Function

@Service
class PageSerivce(
    private val itemRepository: ItemRepository,
    private val cocktailRepository: CocktailRepository,
    private val queryFactory: JPAQueryFactory,
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

    fun getSearchPage(requestDTO: PageRequestDTO, sort: String, category: String, keyword: String) : Any {
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
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> itemToSearchIteminfoDTO(Item)}
            return setItemPageResponseDTO(PageResultDTO(result,fn))
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
            val fn: Function<Item, SearchItemInfoDTO> = Function { Item -> itemToSearchIteminfoDTO(Item)}
            return setItemPageResponseDTO(PageResultDTO(result,fn))
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
            val fn: Function<Cocktail, SearchCocktailInfoDTO> = Function { Cocktail -> cocktailToSearchCocktailInfoDTO(Cocktail) }
            return setCocktailPageResponseDTO(PageResultDTO(result,fn))
        }
    }
}