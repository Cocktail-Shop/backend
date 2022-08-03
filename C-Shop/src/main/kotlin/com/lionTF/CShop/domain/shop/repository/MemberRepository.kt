package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
}