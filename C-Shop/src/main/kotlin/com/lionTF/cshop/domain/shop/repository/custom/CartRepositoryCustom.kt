package com.lionTF.cshop.domain.shop.repository.custom

import com.lionTF.cshop.domain.shop.controller.dto.FindCartDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CartRepositoryCustom {

    fun findCartInfo(memberId: Long, pageable: Pageable): Page<FindCartDTO>
}
