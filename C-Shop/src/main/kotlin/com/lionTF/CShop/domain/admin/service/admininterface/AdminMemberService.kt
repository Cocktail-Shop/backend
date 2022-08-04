package com.lionTF.CShop.domain.admin.service.admininterface

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.member.models.Member
import java.util.*

interface AdminMemberService {

    fun deleteMembers(deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO

    fun existedMember(memberId: Long): Optional<Member>

    fun formToExistedMembers(memberIdList: MutableList<Long>): Boolean
}