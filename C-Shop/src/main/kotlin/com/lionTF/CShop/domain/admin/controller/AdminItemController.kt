package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.createItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.createItemResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class AdminItemController(
    private final val adminItemService: AdminItemService,
) {

    // 상품 등록
    @PostMapping("/admins/items")
    fun createItem(@RequestBody createItemDTO: createItemDTO): createItemResultDTO {
        return adminItemService.createItem(createItemDTO)
    }
}