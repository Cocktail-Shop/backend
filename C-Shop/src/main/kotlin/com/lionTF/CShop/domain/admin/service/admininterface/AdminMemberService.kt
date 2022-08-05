package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO

interface AdminMemberService {

    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO
}