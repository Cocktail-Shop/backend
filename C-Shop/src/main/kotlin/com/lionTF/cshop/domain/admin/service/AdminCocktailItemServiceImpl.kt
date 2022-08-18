package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.repository.AdminCocktailItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminCocktailItemService
import org.springframework.stereotype.Service

@Service
class AdminCocktailItemServiceImpl(
    private val adminCocktailItemRepository: AdminCocktailItemRepository,

    ) : AdminCocktailItemService {

    // itemId 가져오기
    override fun getItemIds(cocktailId: Long): MutableList<Long> {
        return adminCocktailItemRepository.findItemIdByCocktailId(cocktailId)
    }
}
