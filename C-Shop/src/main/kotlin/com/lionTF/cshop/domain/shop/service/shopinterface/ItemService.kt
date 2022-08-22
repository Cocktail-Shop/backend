package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.shop.controller.dto.ItemResultDTO

interface ItemService {

    fun findByItemId(itemId: Long): ItemResultDTO
}
