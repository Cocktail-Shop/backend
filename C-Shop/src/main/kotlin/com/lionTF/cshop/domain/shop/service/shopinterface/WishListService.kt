package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.shop.controller.dto.WishListDTO

interface WishListService {

    fun createWishList(memberId: Long?, itemId: Long): AdminResponseDTO

    fun getWishList(memberId: Long?): List<WishListDTO>

    fun deleteWishList(memberId: Long?, wishListId: Long): AdminResponseDTO
}