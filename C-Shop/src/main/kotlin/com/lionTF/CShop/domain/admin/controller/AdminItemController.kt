package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
@RequestMapping("/admins")
class AdminItemController(
    private val adminItemService: AdminItemService,
) {

    // 전체 상품 조회
    @GetMapping("all-item")
    fun getAllItems(
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("items", adminItemService.getAllItems(pageable))
        return "admins/item/getAllItem"
    }

    // 상품명으로 상품 조회
    @GetMapping("search/items")
    fun getItemsByName(
        @RequestParam("keyword") keyword: String,
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("items", adminItemService.getItemsByName(keyword, pageable))
        return "admins/item/getItemsByName"
    }

    // 상품 등록 페이지
    @GetMapping("items")
    fun getCreateItemForm(model: Model): String {
        model.addAttribute("createItemDTO", RequestCreateItemDTO.toFormDTO())
        return "admins/item/createItemForm"
    }


    // 상품 등록
    @PostMapping("items")
    fun createItem(
        model: Model,
        requestCreateItemDTO: RequestCreateItemDTO
    ): String {
        model.addAttribute("result", adminItemService.createItem(requestCreateItemDTO))
        return "global/message"
    }

    // 상품 수정 페이지
    @GetMapping("items/{itemId}")
    fun getItem(@PathVariable("itemId") itemId: Long, model: Model): String {
        val item = adminItemService.findItem(itemId)
        model.addAttribute("items", item)
        model.addAttribute("items", RequestUpdateItemDTO.formDTOFromResponseItemDTO(item))
        return "admins/item/updateItemForm"
    }

    // 상품 수정
    @PutMapping("items/{itemId}")
    fun updateItem(
        @PathVariable("itemId") itemId: Long,
        requestCreateItemDTO: RequestCreateItemDTO,
        model: Model
    ): String {
        model.addAttribute("result", adminItemService.updateItem(itemId, requestCreateItemDTO))
        return "global/message"
    }

    // 한개 상품 삭제
    @DeleteMapping("items/{itemId}")
    fun deleteOneItem(
        @PathVariable("itemId") itemId: Long,
        model: Model
    ): String {
        model.addAttribute("result", adminItemService.deleteOneItem(itemId))
        return "global/message"
    }


    // 라디오 박스에 카테고리 이넘타입의 내용을 배열로 반환
    @ModelAttribute("category")
    fun itemTypes(): Array<Category> {
        return Category.values()
    }


//    @ModelAttribute("itemIds")
//    fun favorite(pageable: Pageable): Map<Long, String> {
//        var map: MutableMap<Long, String> = LinkedHashMap()
//        var items = adminItemService.getAllItems(pageable)
//
//        for (item in items.result!!.content) {
//            map[item.itemId] = ""
//        }
//
//        return map
//    }

    //    복수 상품 삭제
//    @DeleteMapping("items")
//    fun deleteItem(@RequestBody deleteItemDTO: DeleteItemDTO): DeleteItemResultDTO {
//        return adminItemService.deleteItems(deleteItemDTO)
//    }
}
