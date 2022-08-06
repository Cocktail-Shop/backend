package com.lionTF.CShop.domain.admin.repository.custom

import com.lionTF.CShop.domain.admin.controller.dto.FindMembersDTO
import com.lionTF.CShop.domain.member.models.Member

interface AdminMemberRepositoryCustom {

    fun findMembersInfo(keyword: String): List<Member>?
}