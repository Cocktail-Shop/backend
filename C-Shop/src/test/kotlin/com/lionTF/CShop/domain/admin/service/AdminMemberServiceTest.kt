package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteFailMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteSuccessMembersResultDTO
import com.lionTF.CShop.domain.admin.repository.AdminMemberRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.shop.repository.MemberRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminMemberServiceTest{

    @Autowired
    var adminMemberService: AdminMemberService? = null

    @Autowired
    var memberRepository: MemberRepository? = null

    var member: Member? = null

    @BeforeEach
    fun init() {
        var member1 = Member(
            memberName = "test"
        )

        member = memberRepository!!.save(member1!!)
    }


    @Test
    @DisplayName("회원 삭제 test")
    fun memberDeleteTest() {
        //given
        var memberIds: MutableList<Long> = mutableListOf()

        memberIds.add(member!!.memberId)

        var deleteMembersDTO = DeleteMembersDTO(
            memberIds
        )

        //when
        val deleteMembers = adminMemberService!!.deleteMembers(deleteMembersDTO)

        //then
        assertThat(deleteMembers.status).isEqualTo(setDeleteSuccessMembersResultDTO().status)
        assertThat(deleteMembers.message).isEqualTo(setDeleteSuccessMembersResultDTO().message)
        assertThat(member!!.memberStatus).isEqualTo(false)
    }

    @Test
    @DisplayName("존재하지 않는 회원 삭제시 예외 test")
    fun memberDeleteExceptionTest() {
        //given
        var memberIds: MutableList<Long> = mutableListOf()

        memberIds.add(10L)

        var deleteMembersDTO = DeleteMembersDTO(
            memberIds
        )

        //when
        val deleteMembers = adminMemberService!!.deleteMembers(deleteMembersDTO)

        //then
        assertThat(deleteMembers.status).isEqualTo(setDeleteFailMembersResultDTO().status)
        assertThat(deleteMembers.message).isEqualTo(setDeleteFailMembersResultDTO().message)
        assertThat(member!!.memberStatus).isEqualTo(true)
    }
}