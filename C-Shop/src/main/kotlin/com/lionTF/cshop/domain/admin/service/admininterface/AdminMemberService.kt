package com.lionTF.cshop.domain.admin.service.admininterface

import com.lionTF.cshop.domain.admin.controller.dto.*
import org.springframework.data.domain.Pageable

interface AdminMemberService {


    fun findMembers(keyword: String, pageable: Pageable): MembersSearchDTO

    fun getAllMembers(pageable: Pageable): MembersSearchDTO

    fun deleteOneMember(memberId: Long): AdminResponseDTO

    //    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO
}
