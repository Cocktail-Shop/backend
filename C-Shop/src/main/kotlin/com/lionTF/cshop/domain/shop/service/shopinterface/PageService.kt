package com.lionTF.cshop.domain.shop.service.shopinterface

import com.lionTF.cshop.domain.shop.controller.dto.PageRequestDTO

interface PageService {

    fun getPage(requestDTO: PageRequestDTO, sort: String, category: String) : Any

    fun getSearchPage(requestDTO: PageRequestDTO, sort: String, category: String, keyword: String) : Any
}
