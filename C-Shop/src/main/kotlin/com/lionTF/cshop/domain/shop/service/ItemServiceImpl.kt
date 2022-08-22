package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.repository.ItemRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.ItemService
import org.springframework.stereotype.Service


@Service
class ItemServiceImpl (
    private val itemRepository: ItemRepository,
) : ItemService{

    override fun findByItemId(itemId: Long): ItemResultDTO {
        val item =ItemDTO.fromItem(itemRepository.getReferenceById(itemId))
        return ItemResultDTO.setItemResultDTO(item)
    }
}
