package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Category
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class PageRequestDTO(
    var page: Int = 1,
    var size: Int = 10,
    var sort: String? = null,
    var category: Category? = null,
){
    fun getPageable(sort: Sort): Pageable{
        return PageRequest.of(page-1,size,sort)
    }
}
