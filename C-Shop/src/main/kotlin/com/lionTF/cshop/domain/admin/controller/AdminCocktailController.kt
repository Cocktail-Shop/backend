package com.lionTF.cshop.domain.admin.controller

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.service.admininterface.AdminCocktailItemService
import com.lionTF.cshop.domain.admin.service.admininterface.AdminCocktailService
import com.lionTF.cshop.domain.admin.service.admininterface.AdminItemService
import com.lionTF.cshop.domain.admin.service.admininterface.ImageService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.util.LinkedHashMap

import org.springframework.web.bind.annotation.ModelAttribute
import java.time.LocalDateTime

@Controller
@RequestMapping("/admins")
class AdminCocktailController(
    private val adminCocktailItemService: AdminCocktailItemService,
    private val adminCocktailService: AdminCocktailService,
    private val adminItemService: AdminItemService,
    private val imageService: ImageService
) {

    // 전체 칵테일 조회
    @GetMapping("all-cocktails")
    fun getCocktails(pageable: Pageable, model: Model): String {
        model.addAttribute("cocktails", adminCocktailService.getAllCocktail(pageable))
        return "admins/cocktail/getAllCocktail"
    }

    // 칵테일 이름 검색 페이지
    @GetMapping("search/cocktails")
    fun getCocktailByName(
        @RequestParam("cocktailName") cocktailName: String,
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("cocktails", adminCocktailService.getCocktailsByName(cocktailName, pageable))
        return "admins/cocktail/getCocktailsByName"
    }

    // 칵테일 상품 등록 페이지
    @GetMapping("cocktails")
    fun getCreateCocktailForm(model: Model): String {
        model.addAttribute("createCocktailDTO", CocktailCreateRequestDTO.toFormDTO())
        return "admins/cocktail/createCocktailForm"
    }

    // 칵테일 상품 등록
    @PostMapping("cocktails")
    fun createCocktail(requestCreateCocktailDTO: CocktailCreateRequestDTO, model: Model): String {
        val cocktailImgUrl = getImageUrl(requestCreateCocktailDTO)
        model.addAttribute("result", adminCocktailService.createCocktail(requestCreateCocktailDTO, cocktailImgUrl))
        return "global/message"
    }


    // 칵테일 상세보기(수정) 페이지
    @GetMapping("cocktails/{cocktailId}")
    fun getCocktail(@PathVariable("cocktailId") cocktailId: Long, model: Model): String {
        val itemIds = adminCocktailItemService.getItemIds(cocktailId)
        val cocktail = adminCocktailService.findCocktail(cocktailId, itemIds)
        model.addAttribute("cocktails", cocktail)
        model.addAttribute("cocktails", CocktailUpdateRequestDTO.toFormDTO(cocktail, itemIds))

        return "admins/cocktail/updateCocktailForm"
    }

    // 칵테일 수정
    @PutMapping("cocktails/{cocktailId}")
    fun updateCocktail(
        @PathVariable("cocktailId") cocktailId: Long,
        requestCreateCocktailDTO: CocktailCreateRequestDTO,
        model: Model
    ): String {
        val cocktail = adminCocktailService.findCocktailById(cocktailId)

        when (requestCreateCocktailDTO.cocktailImgUrl?.isEmpty) {
            true -> {
                model.addAttribute(
                    "result",
                    adminCocktailService.updateCocktail(
                        requestCreateCocktailDTO,
                        cocktailId,
                        requestCreateCocktailDTO.itemIds,
                        cocktail.cocktailImgUrl
                    )
                )
            }
            else -> {
                val objectName: List<String> = cocktail.cocktailImgUrl.split("/")

                imageService.requestToken()

                if (cocktail.cocktailImgUrl.isNotEmpty()) {
                    imageService.deleteObject(objectName[objectName.size - 1])
                }

                val cocktailImgUrl = getImageUrl(requestCreateCocktailDTO)
                model.addAttribute(
                    "result",
                    adminCocktailService.updateCocktail(
                        requestCreateCocktailDTO,
                        cocktailId,
                        requestCreateCocktailDTO.itemIds,
                        cocktailImgUrl
                    )
                )
            }
        }

        return "global/message"
    }


    // 한개의 칵테일 삭제
    @DeleteMapping("cocktails/{cocktailId}")
    fun deleteOneCocktail(@PathVariable("cocktailId") cocktailId: Long, model: Model): String {
        model.addAttribute("result", adminCocktailService.deleteOneCocktail(cocktailId))
        return "global/message"
    }


    @ModelAttribute("itemIds")
    fun getItems(): Map<Long, String> {
        val map: MutableMap<Long, String> = LinkedHashMap()
        val items = adminItemService.getItems()

        items.map { item ->
            map[item.itemId] = item.itemName
        }

        return map
    }

    // API 를 통해 이미지 URL 을 가져오는 함
    private fun getImageUrl(requestCreateCocktailDTO: CocktailCreateRequestDTO): String? {
        imageService.requestToken()
        val cocktailImgUrl = requestCreateCocktailDTO.cocktailImgUrl?.let {
            imageService.uploadObject(requestCreateCocktailDTO.cocktailName + LocalDateTime.now().toString(), it)
        }
        return cocktailImgUrl
    }
}
