package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.WishListService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class WishListController(
    private val wishListService: WishListService
) {

    @GetMapping("wish-list")
    fun getWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        model: Model
    ): String {

        return "shop/wishList"
    }

    @PostMapping("wish-list/{itemId}")
    fun createWishList(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        @PathVariable("itemId") itemId: Long,
        model: Model
    ): String {
        val memberId = authMemberDTO?.memberId

        model.addAttribute("result", wishListService.createWishList(memberId, itemId))
        return "global/message"
    }
}