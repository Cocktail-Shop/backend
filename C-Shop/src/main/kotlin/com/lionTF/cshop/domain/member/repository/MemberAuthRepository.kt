package com.lionTF.cshop.domain.member.repository

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberAuthRepository : JpaRepository<Member, Long> {

    fun findById(id: String): Member?

    fun findByEmail(email: String): Member?

    fun findByIdOrEmail(id: String, email: String): List<Member>

    fun findByIdAndEmail(id: String, email: String): Member?

    fun findByMemberNameAndEmail(memberName: String, email: String): Member?

    fun findByMemberId(memberId: Long?): Member?
}
