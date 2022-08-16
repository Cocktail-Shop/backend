package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
}