package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.CocktailWishListService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/wish-list/cocktails")
class CocktailWishListController(
    private val cocktailWishListService: CocktailWishListService
) {

    @GetMapping("")
    fun getWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PageableDefault(size = 4) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("wishList", cocktailWishListService.getWishList(authMemberDTO.memberId, pageable))
        return "shop/cocktailWishList"
    }

    @PostMapping("{cocktailId}")
    fun createWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("cocktailId") cocktailId: Long,
        model: Model
    ): String {
        model.addAttribute("result", cocktailWishListService.createWishList(authMemberDTO.memberId, cocktailId))
        return "global/message"
    }

    @DeleteMapping("{cocktailWishListId}")
    fun deleteWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        @PathVariable("cocktailWishListId") cocktailWishListId: Long,
        model: Model
    ): String {
        model.addAttribute("result", cocktailWishListService.deleteWishList(authMemberDTO.memberId, cocktailWishListId))
        return "global/message"
    }
}
