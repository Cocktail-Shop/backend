package com.lionTF.cshop.domain.member.repository

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface MemberAuthRepository:JpaRepository<Member,Long> {

    fun findById(id:String):Member?

    fun findByIdAndEmail(id:String,email: String):Member?

    fun findByMemberNameAndEmail(memberName:String,email:String):Member?

    fun findByMemberId(memberId:Long?):Member?
}
