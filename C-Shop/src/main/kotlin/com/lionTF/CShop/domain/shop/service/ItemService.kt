package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.querydsl.core.BooleanBuilder
//import com.querydsl.core.QueryFactory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service


@Service
class ItemService (
    private val itemRepository: ItemRepository,
    private val queryFactory: JPAQueryFactory,
){

    // 상품 단건 조회를 위한 메소드. 상품 id로 상품 검색
    fun findByItemId(itemId: Long): ItemResultDTO {
        val item =itemToItemDTO(itemRepository.getReferenceById(itemId))
        return item?.let { setItemResultDTO(it) }!!
    }

    //상품 검색, 키워드가 포함되어 있는 상품 찾기
    fun getDataList(keyword: String) : List<SearchItemInfoDTO>{
        val booleanBuilder:BooleanBuilder = BooleanBuilder()
        booleanBuilder.and(item.itemName.contains(keyword))
        val itemList = queryFactory.selectFrom(item).where(booleanBuilder).fetch()
        val itemDTOList: MutableList<SearchItemInfoDTO> = mutableListOf()

        for(item in itemList){
            itemDTOList.add(itemToSearchIteminfoDTO(item))
        }

        return itemDTOList
    }

}
