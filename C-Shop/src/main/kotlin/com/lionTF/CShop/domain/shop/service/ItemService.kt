package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.querydsl.core.BooleanBuilder
//import com.querydsl.core.QueryFactory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
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

    fun findByItemIdTest(itemId: Long): ItemDTO {
        return itemToItemDTO(itemRepository.getReferenceById(itemId))

    }


}
