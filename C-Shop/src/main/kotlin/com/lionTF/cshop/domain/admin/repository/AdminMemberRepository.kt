package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.repository.custom.AdminMemberRepositoryCustom
import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AdminMemberRepository : JpaRepository<Member, Long>, AdminMemberRepositoryCustom {

    @Query("select m from Member m where m.id = :memberId")
    fun findMember(@Param("memberId") memberId: Long): Member?

}
