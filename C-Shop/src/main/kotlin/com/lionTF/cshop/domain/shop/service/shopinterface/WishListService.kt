package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO

interface WishListService {

    fun createWishList(memberId: Long?, itemId: Long): AdminResponseDTO
}