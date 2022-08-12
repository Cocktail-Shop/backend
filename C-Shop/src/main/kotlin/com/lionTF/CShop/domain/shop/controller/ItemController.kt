package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.shop.controller.dto.AddCartItemRequestDTO
import com.lionTF.CShop.domain.shop.controller.dto.ItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.ItemResultDTO
import com.lionTF.CShop.domain.shop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ItemController (
    private val itemService: ItemService
    ){

    // item전체 조회. 테스트를 위한 용도임.
//    @GetMapping(path=["/allItem"])
//    fun getAllItems(): List<ItemDTO>{
//        return itemService.findAllItem()
//    }

    //상품 단건 조회
    @GetMapping(path=["/items/{itemId}"])
    fun getItemById(@PathVariable("itemId") itemId: Long, model: Model, addCartItemRequestDTO: AddCartItemRequestDTO): String {
        model.addAttribute("item",itemService.findByItemId(itemId))
        model.addAttribute("addCartItemRequestDTO",addCartItemRequestDTO)
        return "shop/singleItem"
    }

//    @GetMapping(path=["items/all"])
//    fun getAllItemsTest(): List<Item> {
//        return itemService.finyAllItems()
//    }
}