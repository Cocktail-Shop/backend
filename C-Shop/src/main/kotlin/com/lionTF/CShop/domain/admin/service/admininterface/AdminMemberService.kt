package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AdminMemberService {

    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO

    fun deleteOneMember(memberId: Long): DeleteMembersResultDTO

    fun findMembers(keyword: String, pageable: Pageable): Page<FindMembersDTO>

    fun getAllMembers(pageable: Pageable): Page<FindMembersDTO>
}