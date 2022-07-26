package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
import com.lionTF.CShop.domain.shop.service.ItemService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.web.bind.annotation.PathVariable

@RestController
//@RequestMapping("/test")
class ItemController (
    private val itemService: ItemService
    ){

    @GetMapping(path=["/allItem"])
    fun getAllItems(model: Model) : List<ReadItemDTO>{
        val list: List<ReadItemDTO> = itemService.findAllItem()
        model.addAttribute("itemList", list)
        return itemService.findAllItem()
    }

    @GetMapping(path=["/items/{itemId}"])
    fun getItemById(model: Model, @PathVariable("itemId") itemId: Long): Optional<ReadItemDTO>? {
        model.addAttribute("item",itemService.findByItemId(itemId))
        return itemService.findByItemId(itemId)
    }

}