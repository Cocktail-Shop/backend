package com.lionTF.CShop.domain.shop.service.shopinterface

import com.lionTF.CShop.domain.shop.controller.dto.PageRequestDTO

interface PageSerivce {
    //전체 조회 페이지 처리
    fun getPage(requestDTO: PageRequestDTO, sort: String, category: String) : Any

    //검색 조회 페이지 처리
    fun getSearchPage(requestDTO: PageRequestDTO, sort: String, category: String, keyword: String) : Any
}