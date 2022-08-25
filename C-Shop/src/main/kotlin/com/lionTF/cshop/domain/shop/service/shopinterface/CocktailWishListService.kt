package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.shop.controller.dto.CocktailWishListDTO
import com.lionTF.cshop.domain.shop.controller.dto.ItemWishListDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CocktailWishListService {

    fun createWishList(memberId: Long, itemId: Long): AdminResponseDTO

    fun getWishList(memberId: Long, pageable: Pageable): Page<CocktailWishListDTO>

    fun deleteWishList(memberId: Long, wishListId: Long): AdminResponseDTO
}
