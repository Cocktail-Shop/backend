package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.shop.controller.dto.ReadItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ReadItemResultDTO
import com.lionTF.CShop.domain.shop.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.web.bind.annotation.PathVariable

@RestController
class ItemController (
    private val itemService: ItemService
    ){

    // item전체 조회. 테스트를 위한 용도임.
    @GetMapping(path=["/allItem"])
    fun getAllItems(model: Model) : List<ReadItemDTO>{
        val list: List<ReadItemDTO> = itemService.findAllItem()
        model.addAttribute("itemList", list)
        return itemService.findAllItem()
    }

    //상품 단건 조회
    @GetMapping(path=["/items/{itemId}"])
    fun getItemById(model: Model, @PathVariable("itemId") itemId: Long): ReadItemResultDTO {
        model.addAttribute("item",itemService.findByItemId(itemId))
        return itemService.findByItemId(itemId)
    }


}