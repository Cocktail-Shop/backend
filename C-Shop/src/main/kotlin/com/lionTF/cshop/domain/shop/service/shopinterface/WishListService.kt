package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.shop.controller.dto.WishListDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface WishListService {

    fun createWishList(memberId: Long?, itemId: Long): AdminResponseDTO

    fun getWishList(memberId: Long?, pageable: Pageable): Page<WishListDTO>

    fun deleteWishList(memberId: Long?, wishListId: Long): AdminResponseDTO
}
