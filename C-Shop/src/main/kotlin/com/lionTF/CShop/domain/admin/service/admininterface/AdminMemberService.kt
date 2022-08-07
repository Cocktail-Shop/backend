package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersResultDTO
import org.springframework.data.domain.Pageable

interface AdminMemberService {

    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO

    fun findMembers(keyword: String, pageable: Pageable): FindMembersResultDTO?

    fun getAllMembers(pageable: Pageable): FindMembersResultDTO?
}