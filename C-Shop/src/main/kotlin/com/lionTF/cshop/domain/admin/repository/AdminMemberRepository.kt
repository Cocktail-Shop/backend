package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.repository.custom.AdminMemberRepositoryCustom
import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository

interface AdminMemberRepository: JpaRepository<Member, Long>, AdminMemberRepositoryCustom {

}
