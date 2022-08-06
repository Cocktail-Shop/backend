package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersResultDTO

interface AdminMemberService {

    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO

    fun findMembers(keyword: String): FindMembersResultDTO?

    fun getAllMembers(): FindMembersResultDTO?
}