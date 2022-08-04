package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteItemResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateItemResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import com.lionTF.CShop.domain.shop.controller.dto.ItemResultDTO
import com.lionTF.CShop.domain.shop.service.ItemService
import org.springframework.web.bind.annotation.*

@RestController
class AdminItemController(
    private val adminItemService: AdminItemService,
    private val itemService: ItemService,
) {

    // 상품 등록
    @PostMapping("/admins/items")
    fun createItem(@RequestBody createItemDTO: CreateItemDTO): CreateItemResultDTO {
        return adminItemService.createItem(createItemDTO)
    }

    // 상품 수정
    @PutMapping("/admins/items/{itemId}")
    fun updateItem(@PathVariable("itemId") itemId: Long,
                   @RequestBody createItemDTO: CreateItemDTO): CreateItemResultDTO {
        return adminItemService.updateItem(itemId, createItemDTO)
    }

    // 상품 삭제
    @DeleteMapping("/admins/items")
    fun deleteItem(@RequestBody deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
        return adminItemService.deleteItems(deleteItemDTO)
    }

    // 단건 상품 조회
    @GetMapping("/admins/items/{itemId}")
    fun getItem(@PathVariable("itemId") itemId: Long): ItemResultDTO {
        return itemService.findByItemId(itemId)
    }

}