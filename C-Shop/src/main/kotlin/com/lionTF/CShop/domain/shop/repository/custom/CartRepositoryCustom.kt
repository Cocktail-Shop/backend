package com.lionTF.CShop.domain.shop.repository.custom

import com.lionTF.CShop.domain.shop.controller.dto.FindCartDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CartRepositoryCustom {

    fun findCartInfo(pageable: Pageable): Page<FindCartDTO>
}