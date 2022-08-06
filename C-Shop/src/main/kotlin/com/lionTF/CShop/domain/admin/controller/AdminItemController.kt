package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.CreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteItemResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.CreateItemResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import com.lionTF.CShop.domain.shop.controller.dto.ItemResultDTO
import com.lionTF.CShop.domain.shop.service.ItemService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class AdminItemController(
    private val adminItemService: AdminItemService,
    private val itemService: ItemService,
) {

    // 상품 등록 페이지
    @GetMapping("/admins/items")
    fun getCreateItemForm(model: Model): String {
        val createItemDTO = CreateItemDTO()
        model.addAttribute("createItemDTO", createItemDTO)

        return "admins/createItemForm"
    }

    // 상품 등록
    @PostMapping("/admins/items")
    fun createItem(
        createItemDTO: CreateItemDTO,
        model: Model
    ): String {
        model.addAttribute("createItemDTO", adminItemService.createItem(createItemDTO))
        return "redirect:/admins/all-item"
    }


    // 전체 상품 조회
    @GetMapping("/admins/all-item")
    fun getAllItems(model: Model): String {
        model.addAttribute("itemList", adminItemService.getAllItems())
        return "admins/getAllItem"
    }

    // 상품 수정
    @PutMapping("/admins/items/{itemId}")
    fun updateItem(
        @PathVariable("itemId") itemId: Long,
        @ModelAttribute("item") item: CreateItemDTO
    ): String {
        adminItemService.updateItem(itemId, item)
        return "redirect:/"
    }

    // 상품 삭제
    @DeleteMapping("/admins/items")
    fun deleteItem(@RequestBody deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
        return adminItemService.deleteItems(deleteItemDTO)
    }

    // 단건 상품 조회
    @GetMapping("/admins/items/{itemId}")
    fun getItem(@PathVariable("itemId") itemId: Long, model: Model): String {
        model.addAttribute("item", itemService.findByItemIdTest(itemId))
        return "admins/updateItemForm"
    }

}