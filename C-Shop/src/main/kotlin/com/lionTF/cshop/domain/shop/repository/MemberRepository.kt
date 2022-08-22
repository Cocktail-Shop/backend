package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}
