package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Category
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

data class PageRequestDTO(
    var page: Int = 1,
    var size: Int = 10,
    var sort: String? = null,
    var category: Category? = null,
    var keyword: String? = null
) {

    fun getPageable(sort: Sort): Pageable {
        return PageRequest.of(page - 1, size, sort)
    }

    fun getPageable(sort: Sort, keyword: String?): Pageable {
        this.keyword = keyword
        return PageRequest.of(page - 1, size, sort)
    }
}
