package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.*
import org.springframework.data.domain.Pageable

interface AdminMemberService {

//    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO

    fun deleteOneMember(memberId: Long): AdminResponseDTO

    fun findMembers(keyword: String, pageable: Pageable): ResponseSearchMembersResultDTO

    fun getAllMembers(pageable: Pageable): ResponseSearchMembersResultDTO
}