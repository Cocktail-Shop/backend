package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.lionTF.CShop.domain.shop.service.shopinterface.ItemService
//import com.querydsl.core.QueryFactory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Service


@Service
class ItemServiceImpl (
    private val itemRepository: ItemRepository,
    private val queryFactory: JPAQueryFactory,
) : ItemService{

    // 상품 단건 조회를 위한 메소드. 상품 id로 상품 검색
    override fun findByItemId(itemId: Long): ItemResultDTO {
        val item =ItemDTO.fromItem(itemRepository.getReferenceById(itemId))
        return item?.let { ItemResultDTO.setItemResultDTO(it) }!!
    }

    fun findByItemIdTest(itemId: Long): ItemDTO {
        return ItemDTO.fromItem(itemRepository.getReferenceById(itemId))

    }


}
