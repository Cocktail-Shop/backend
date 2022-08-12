package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailItemService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AdminCocktailItemServiceImpl(
    private val adminCocktailItemRepository: AdminCocktailItemRepository,

): AdminCocktailItemService {

    // itemId 가져오기
    override fun getItemIds(cocktailId: Long): MutableList<Long> {
        return adminCocktailItemRepository.findItemIdByCocktailId(cocktailId)
    }

}