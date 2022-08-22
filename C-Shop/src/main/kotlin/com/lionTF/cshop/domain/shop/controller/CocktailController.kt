package com.lionTF.cshop.domain.shop.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.shop.controller.dto.AddCartCocktailItemInfoDTO
import com.lionTF.cshop.domain.shop.controller.dto.AddCartCocktailItemRequestDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderItemDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.CocktailService
import com.lionTF.cshop.domain.shop.service.shopinterface.OrderService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class CocktailController(
    private val cocktailService: CocktailService,
    private val orderService: OrderService,
) {

    @GetMapping(path = ["/items/cocktails/{cocktailId}"])
    fun getCocktailById(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,
        @PathVariable("cocktailId") cocktailId: Long,
        model: Model
    ): String {

        val cocktail = cocktailService.findByCocktailId(cocktailId)

        val address = authMemberDTO?.memberId?.let {
            orderService.getAddress(it)
        }

        val cocktailOrderInfoDTO = cocktail.result.cocktailItems.map {
            RequestOrderItemDTO.fromCocktailItemDTO(it)
        }

        val cocktailCartInfoDTO = cocktail.result.cocktailItems.map {
            AddCartCocktailItemInfoDTO.fromCocktailItemDTO(it)
        }

        val requestOrderInfoDTO = RequestOrderInfoDTO.toFormRequestCocktailOrderInfoDTO(
            cocktailOrderInfoDTO as MutableList<RequestOrderItemDTO>,
            address!!
        )

        val addCartCocktailItemDTO = AddCartCocktailItemRequestDTO.toFormRequestCocktailCartInfoDTO(cocktailCartInfoDTO as MutableList<AddCartCocktailItemInfoDTO>)

        model.addAttribute("cocktail", cocktail)
        model.addAttribute("addCartItemRequestDTO", addCartCocktailItemDTO)
        model.addAttribute("requestOrderInfoDTO", requestOrderInfoDTO)

        return "shop/singleCocktail"
    }
}
