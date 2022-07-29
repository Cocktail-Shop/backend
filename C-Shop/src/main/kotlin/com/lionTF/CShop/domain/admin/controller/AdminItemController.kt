package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.createItemResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
class AdminItemController(
    private final val adminItemService: AdminItemService,
) {

    // 상품 등록
    @PostMapping("/admins/items")
    fun createItem(@RequestBody createItemDTO: CreateItemDTO): createItemResultDTO {
        return adminItemService.createItem(createItemDTO)
    }

    // 상품 수정
    @PutMapping("/admins/items/{itemId}")
    fun updateItem(@PathVariable("itemId") itemId: Long, @RequestBody createItemDTO: CreateItemDTO): createItemResultDTO {
        return adminItemService.updateItem(itemId, createItemDTO)
    }
}