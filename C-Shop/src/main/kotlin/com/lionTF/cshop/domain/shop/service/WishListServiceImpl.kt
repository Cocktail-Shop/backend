package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.shop.service.shopinterface.WishListService
import org.springframework.stereotype.Service

@Service
class WishListServiceImpl(

) : WishListService {
    override fun createWishList(memberId: Long?): AdminResponseDTO {
        // TODO 찜하기 기능 추가예정
        return AdminResponseDTO.toFailCreateCocktailByNoContentResponseDTO()
    }
}