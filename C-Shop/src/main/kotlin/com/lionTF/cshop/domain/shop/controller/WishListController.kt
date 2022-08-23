package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.WishListService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/wish-list")
class WishListController(
    private val wishListService: WishListService
) {

    @GetMapping("")
    fun getWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PageableDefault(size = 4) pageable: Pageable,
        model: Model
    ): String {
        val memberId = authMemberDTO.memberId
        model.addAttribute("wishList", wishListService.getWishList(memberId, pageable))
        return "shop/wishList"
    }

    @PostMapping("{itemId}")
    fun createWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("itemId") itemId: Long,
        model: Model
    ): String {
        val memberId = authMemberDTO.memberId

        model.addAttribute("result", wishListService.createWishList(memberId, itemId))
        return "global/message"
    }

    @DeleteMapping("{wishListId}")
    fun deleteWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("wishListId") wishListId: Long,
        model: Model
    ): String {
        val memberId = authMemberDTO.memberId

        model.addAttribute("result", wishListService.deleteWishList(memberId, wishListId))
        return "global/message"
    }
}
