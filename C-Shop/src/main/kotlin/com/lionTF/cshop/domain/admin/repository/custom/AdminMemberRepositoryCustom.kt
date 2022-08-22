package com.lionTF.cshop.domain.admin.repository.custom

import com.lionTF.cshop.domain.admin.controller.dto.MembersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminMemberRepositoryCustom {

    fun findMembersInfo(keyword: String, pageable: Pageable): Page<MembersDTO>

    fun findAllByMemberStatus(pageable: Pageable): Page<MembersDTO>
}
