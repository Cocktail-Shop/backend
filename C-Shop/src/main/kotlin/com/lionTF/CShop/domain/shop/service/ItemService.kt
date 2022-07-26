package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.lionTF.CShop.domain.shop.repository.CocktailRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ItemService (
    private val itemRepository: ItemRepository,
){
    @Transactional
    fun findAllItem(): List<ReadItemDTO> {
        val items = itemRepository.findAll()
        return items.map {it.toReadItemDTO()}
    }

    @Transactional
    fun findByItemId(itemId: Long): Optional<ReadItemDTO>? {
        val product = itemRepository.findById(itemId)
        return product.map { it.toReadItemDTO() }
    }
}