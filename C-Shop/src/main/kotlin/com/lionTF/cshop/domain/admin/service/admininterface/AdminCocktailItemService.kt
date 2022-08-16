package com.lionTF.cshop.domain.admin.service.admininterface

interface AdminCocktailItemService {
    fun getItemIds(cocktailId: Long): MutableList<Long>
}