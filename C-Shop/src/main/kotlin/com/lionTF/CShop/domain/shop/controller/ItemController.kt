package com.lionTF.CShop.domain.shop.controller

import com.lionTF.CShop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.CShop.domain.shop.controller.dto.AddCartItemRequestDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderInfoDTO
import com.lionTF.CShop.domain.shop.service.shopinterface.ItemService
import com.lionTF.CShop.domain.shop.service.shopinterface.OrderService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class ItemController (
    private val itemService: ItemService,
    private val orderService: OrderService,
    ){


    //상품 단건 조회
    @GetMapping(path=["/items/{itemId}"])
    fun getItemById(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, @PathVariable("itemId") itemId: Long, model: Model, addCartItemRequestDTO: AddCartItemRequestDTO): String {
        val item=itemService.findByItemId(itemId)
        val address=authMemberDTO?.memberId?.let { orderService.getAddress(it) }
        val requestOrderInfoDTO=RequestOrderInfoDTO.toFormRequestItemOrderInfoDTO(item.result, address!!)
        model.addAttribute("item",item)
        model.addAttribute("addCartItemRequestDTO",addCartItemRequestDTO)
        model.addAttribute("requestOrderInfoDTO",requestOrderInfoDTO)
        model.addAttribute("address", authMemberDTO?.memberId?.let { orderService.getAddress(it) })

        return "shop/singleItem"
    }
    
}