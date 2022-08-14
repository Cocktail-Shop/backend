package com.lionTF.CShop.domain.admin.service.admininterface

interface AdminCocktailItemService {
    fun getItemIds(cocktailId: Long): MutableList<Long>
}