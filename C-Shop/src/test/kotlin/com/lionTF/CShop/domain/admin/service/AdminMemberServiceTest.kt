package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteFailMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteSuccessMembersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.shop.repository.MemberRepository
import org.assertj.core.api.Assertions.*
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

    var memberTest1: Member? = null
    var memberTest2: Member? = null
    var memberTest3: Member? = null

    @BeforeEach
    fun init() {
        var member1 = Member(
            memberName = "test1",
            address = "address-test1",
            phoneNumber = "phone-test1",
            id = "id-test1"
        )
        memberTest1 = memberRepository!!.save(member1!!)

        var member2 = Member(
            memberName = "test2",
            address = "address-test2",
            phoneNumber = "phone-test2",
            id = "id-test2"
        )
        memberTest2 = memberRepository!!.save(member2!!)

        var member3 = Member(
            memberName = "test3",
            address = "address-test3",
            phoneNumber = "phone-test3",
            id = "id-test3"
        )
        memberTest3 = memberRepository!!.save(member3!!)
    }


    @Test
    @DisplayName("회원 삭제 test")
    fun memberDeleteTest() {
        //given
        var memberIds: MutableList<Long> = mutableListOf()

        memberIds.add(memberTest1!!.memberId)

        var deleteMembersDTO = DeleteMembersDTO(
            memberIds
        )

        //when
        val deleteMembers = adminMemberService!!.deleteMembers(deleteMembersDTO)

        //then
        assertThat(deleteMembers.status).isEqualTo(setDeleteSuccessMembersResultDTO().status)
        assertThat(deleteMembers.message).isEqualTo(setDeleteSuccessMembersResultDTO().message)
        assertThat(memberTest1!!.memberStatus).isEqualTo(false)
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
        assertThat(memberTest1!!.memberStatus).isEqualTo(true)
    }

    @Test
    @DisplayName("회원 ID로 회원 검색 test")
    fun findMembers() {
        //given
        var keyword: String = "te"

        //when
        val findMembers = adminMemberService!!.findMembers(keyword)

        //then
        assertThat(findMembers[0].id).isEqualTo(memberTest1!!.id)
        assertThat(findMembers[0].phoneNumber).isEqualTo(memberTest1!!.phoneNumber)
        assertThat(findMembers[0].memberName).isEqualTo(memberTest1!!.memberName)
        assertThat(findMembers[0].address).isEqualTo(memberTest1!!.address)

        assertThat(findMembers[1].id).isEqualTo(memberTest2!!.id)
        assertThat(findMembers[1].phoneNumber).isEqualTo(memberTest2!!.phoneNumber)
        assertThat(findMembers[1].memberName).isEqualTo(memberTest2!!.memberName)
        assertThat(findMembers[1].address).isEqualTo(memberTest2!!.address)

        assertThat(findMembers[2].id).isEqualTo(memberTest3!!.id)
        assertThat(findMembers[2].phoneNumber).isEqualTo(memberTest3!!.phoneNumber)
        assertThat(findMembers[2].memberName).isEqualTo(memberTest3!!.memberName)
        assertThat(findMembers[2].address).isEqualTo(memberTest3!!.address)
    }
}