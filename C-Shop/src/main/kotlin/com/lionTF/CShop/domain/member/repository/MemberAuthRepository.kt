package com.lionTF.CShop.domain.member.repository

import com.lionTF.CShop.domain.member.models.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface MemberAuthRepository:JpaRepository<Member,Long> {

    @Query("select m from Member m where m.id = :id")
    fun findById(@Param("id")id:String):Optional<Member>

}