package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.ItemWishListService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/wish-list")
class ItemWishListController(
    private val wishListService: ItemWishListService
) {

    @GetMapping("")
    fun getWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PageableDefault(size = 4) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("wishList", wishListService.getWishList(authMemberDTO.memberId, pageable))
        return "shop/itemWishList"
    }

    @PostMapping("{itemId}")
    fun createWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("itemId") itemId: Long,
        model: Model
    ): String {
        model.addAttribute("result", wishListService.createWishList(authMemberDTO.memberId, itemId))
        return "global/message"
    }

    @DeleteMapping("{wishListId}")
    fun deleteWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("wishListId") wishListId: Long,
        model: Model
    ): String {
        model.addAttribute("result", wishListService.deleteWishList(authMemberDTO.memberId, wishListId))
        return "global/message"
    }
}
