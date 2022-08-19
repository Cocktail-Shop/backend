package com.lionTF.cshop.domain.admin.controller

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.service.admininterface.AdminItemService
import com.lionTF.cshop.domain.admin.service.admininterface.ImageService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ModelAttribute
import java.time.LocalDateTime

@Controller
@RequestMapping("/admins")
class AdminItemController(
    private val adminItemService: AdminItemService,
    private val imageService: ImageService
) {

    // 전체 상품 조회
    @GetMapping("all-item")
    fun getAllItems(
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("items", adminItemService.getAllItems(pageable))
        return "admins/item/getAllItem"
    }

    // 상품명으로 상품 조회
    @GetMapping("search/items")
    fun getItemsByName(
        @RequestParam("itemName") itemName: String,
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("items", adminItemService.getItemsByName(itemName, pageable))
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
        requestCreateItemDTO: RequestCreateItemDTO,
        model: Model
    ): String {
        val itemImgUrl = getImageUrl(requestCreateItemDTO)
        model.addAttribute("result", adminItemService.createItem(requestCreateItemDTO, itemImgUrl))
        return "global/message"
    }

    // 상품 상세보기(수정) 페이지
    @GetMapping("items/{itemId}")
    fun getItem(
        @PathVariable("itemId") itemId: Long,
        model: Model
    ): String {
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
        val item = adminItemService.findItemById(itemId)

        when (requestCreateItemDTO.itemImgUrl?.isEmpty) {
            true -> {
                model.addAttribute(
                    "result",
                    adminItemService.updateItem(itemId, requestCreateItemDTO, item.itemImgUrl)
                )
            }
            else -> {
                val objectName: List<String> = item.itemImgUrl.split("/")

                imageService.requestToken()

                if (item.itemImgUrl.isNotEmpty()) {
                    imageService.deleteObject(objectName[objectName.size - 1])
                }

                val itemImgUrl = getImageUrl(requestCreateItemDTO)
                model.addAttribute("result", adminItemService.updateItem(itemId, requestCreateItemDTO, itemImgUrl))
            }
        }

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

    // API를 통해 이미지 URL을 가져오는 함
    private fun getImageUrl(requestCreateItemDTO: RequestCreateItemDTO): String? {
        imageService.requestToken()
        val itemImgUrl = requestCreateItemDTO.itemImgUrl?.let {
            imageService.uploadObject(
                requestCreateItemDTO.itemName + LocalDateTime.now().toString(),
                it
            )
        }
        return itemImgUrl
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
