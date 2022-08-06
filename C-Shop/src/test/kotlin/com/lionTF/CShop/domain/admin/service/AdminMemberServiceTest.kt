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
    private lateinit var adminMemberService: AdminMemberService

    @Autowired
    private lateinit var memberRepository: MemberRepository

    private var memberTest1: Member? = null
    private var memberTest2: Member? = null
    private var memberTest3: Member? = null

    @BeforeEach
    fun init() {
        var member1 = Member(
            memberName = "test1",
            address = "address-test1",
            phoneNumber = "phone-test1",
            id = "id-test1"
        )
        memberTest1 = memberRepository.save(member1)

        var member2 = Member(
            memberName = "test2",
            address = "address-test2",
            phoneNumber = "phone-test2",
            id = "id-test2"
        )
        memberTest2 = memberRepository.save(member2)

        var member3 = Member(
            memberName = "test3",
            address = "address-test3",
            phoneNumber = "phone-test3",
            id = "id-test3"
        )
        memberTest3 = memberRepository.save(member3)
    }


    @Test
    @DisplayName("회원 삭제 test")
    fun memberDeleteTest() {
        //given
        var memberIds: MutableList<Long> = mutableListOf()

        memberTest1?.let { memberIds.add(it.memberId) }

        var deleteMembersDTO = DeleteMembersDTO(
            memberIds
        )

        //when
        val deleteMembers = adminMemberService.deleteMembers(deleteMembersDTO)

        //then
        assertThat(deleteMembers.status).isEqualTo(setDeleteSuccessMembersResultDTO().status)
        assertThat(deleteMembers.message).isEqualTo(setDeleteSuccessMembersResultDTO().message)
        assertThat(memberTest1?.memberStatus).isEqualTo(false)
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
        val deleteMembers = adminMemberService.deleteMembers(deleteMembersDTO)

        //then
        assertThat(deleteMembers.status).isEqualTo(setDeleteFailMembersResultDTO().status)
        assertThat(deleteMembers.message).isEqualTo(setDeleteFailMembersResultDTO().message)
        assertThat(memberTest1?.memberStatus).isEqualTo(true)
    }

    @Test
    @DisplayName("회원 ID로 회원 검색 test")
    fun findMembers() {
        //given
        var keyword: String = "te"

        //when
        val findMembers = adminMemberService.findMembers(keyword)

        //then
        assertThat(findMembers?.findMembersDTO?.get(0)?.id).isEqualTo(memberTest1?.id)
        assertThat(findMembers?.findMembersDTO?.get(0)?.phoneNumber).isEqualTo(memberTest1?.phoneNumber)
        assertThat(findMembers?.findMembersDTO?.get(0)?.memberName).isEqualTo(memberTest1?.memberName)
        assertThat(findMembers?.findMembersDTO?.get(0)?. address).isEqualTo(memberTest1?.address)

        assertThat(findMembers?.findMembersDTO?.get(1)?.id).isEqualTo(memberTest2?.id)
        assertThat(findMembers?.findMembersDTO?.get(1)?.phoneNumber).isEqualTo(memberTest2?.phoneNumber)
        assertThat(findMembers?.findMembersDTO?.get(1)?.memberName).isEqualTo(memberTest2?.memberName)
        assertThat(findMembers?.findMembersDTO?.get(1)?.address).isEqualTo(memberTest2?.address)

        assertThat(findMembers?.findMembersDTO?.get(2)?.id).isEqualTo(memberTest3?.id)
        assertThat(findMembers?.findMembersDTO?.get(2)?.phoneNumber).isEqualTo(memberTest3?.phoneNumber)
        assertThat(findMembers?.findMembersDTO?.get(2)?.memberName).isEqualTo(memberTest3?.memberName)
        assertThat(findMembers?.findMembersDTO?.get(2)?.address).isEqualTo(memberTest3?.address)
    }

    @Test
    @DisplayName("회원 전체 조회 test")
    fun getAllMembersTest() {
        //given

        //when
        val memberList = adminMemberService.getAllMembers()

        val count: Long = memberRepository.count()

        //then
        assertThat(memberList?.findMembersDTO?.get(0)?.id).isEqualTo(memberTest1?.id)
        assertThat(memberList?.findMembersDTO?.get(1)?.id).isEqualTo(memberTest2?.id)
        assertThat(memberList?.findMembersDTO?.get(2)?.id).isEqualTo(memberTest3?.id)

        assertThat(memberList?.findMembersDTO?.size).isEqualTo(count)
    }
}