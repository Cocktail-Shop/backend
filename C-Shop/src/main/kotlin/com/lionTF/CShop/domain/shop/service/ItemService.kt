package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.models.Item
//import com.lionTF.CShop.domain.admin.models.QItem.item
import com.lionTF.CShop.domain.shop.controller.dto.ItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ItemResultDTO
import com.lionTF.CShop.domain.shop.controller.dto.itemToItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.setItemResultDTO
import com.lionTF.CShop.domain.shop.repository.ItemRepository
//import com.querydsl.core.QueryFactory
//import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ItemService (
    private val itemRepository: ItemRepository,
//    private val queryFactory: JPAQueryFactory,
){
    //item 전체 조회 테스트를 위한 메소드
//    fun findAllItem(): List<ItemDTO> {
//        val items = itemRepository.findAll()
//        return items.map {it.toReadItemDTO()}
//    }

    // 상품 단건 조회를 위한 메소드. 상품 id로 상품 검색
    fun findByItemId(itemId: Long): ItemResultDTO {
        val item =itemToItemDTO(itemRepository.getReferenceById(itemId))
        return item?.let { setItemResultDTO(it) }!!
    }

    //querydsl test용
//    fun finyAllItems(): List<Item> {
//        return queryFactory.selectFrom(item)
//            .fetch()
//    }
}
