package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository

interface AdminMemberRepository: JpaRepository<Member, Long> {
}