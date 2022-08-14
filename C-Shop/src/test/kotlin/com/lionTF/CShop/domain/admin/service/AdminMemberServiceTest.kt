package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.shop.repository.MemberRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
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
    @DisplayName("한명의 회원 삭제 test")
    fun deleteOneMemberTest() {
        //given
        val memberId: Long = memberTest1!!.memberId

        //when
        val deleteOneMember = adminMemberService.deleteOneMember(memberId)

        //then
        assertThat(deleteOneMember.httpStatus).isEqualTo(AdminResponseDTO.toSuccessDeleteMemberResponseDTO().httpStatus)
        assertThat(deleteOneMember.message).isEqualTo(AdminResponseDTO.toSuccessDeleteMemberResponseDTO().message)
        assertThat(memberTest1!!.memberStatus).isEqualTo(false)
        println("deleteOneMember = ${deleteOneMember.message}")
    }

    @Test
    @DisplayName("한명의 회원 삭제 예외 test")
    fun deleteOneMemberExceptionTest() {
        //given
        val memberId: Long = 98L

        //when
        val deleteOneMember = adminMemberService.deleteOneMember(memberId)

        //then
        assertThat(deleteOneMember.httpStatus).isEqualTo(AdminResponseDTO.toFailDeleteMemberResponseDTO().httpStatus)
        assertThat(deleteOneMember.message).isEqualTo(AdminResponseDTO.toFailDeleteMemberResponseDTO().message)
        assertThat(memberTest1!!.memberStatus).isEqualTo(true)
        println("deleteOneMember = ${deleteOneMember.message}")
    }

    private fun generatePageable(page: Int, pageSize: Int): PageRequest = PageRequest.of(page, pageSize)



    @Test
    @DisplayName("회원 전체 조회 test")
    fun getAllMembersTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize)

        //when
        val allMembers = adminMemberService.getAllMembers(pageable)

        val countMember = memberRepository.count()

        //then
        assertThat(allMembers.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allMembers.message).isEqualTo("회원 조회를 성공했습니다.")
        assertThat(allMembers.keyword).isEqualTo("")
        assertThat(allMembers.result!!.totalElements).isEqualTo(countMember)
        assertThat(allMembers.result!!.totalPages).isEqualTo(2)
    }

    @Test
    @DisplayName("회원 ID로 회원 검색 test")
    fun findMembers() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize)
        val keyword: String = "test"

        //when
        val findMembers = adminMemberService.findMembers(keyword, pageable)

        //then
        assertThat(findMembers.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(findMembers.message).isEqualTo("회원 조회를 성공했습니다.")
        assertThat(findMembers.keyword).isEqualTo(keyword)
        assertThat(findMembers.result!!.totalElements).isEqualTo(4)
        assertThat(findMembers.result!!.totalPages).isEqualTo(2)
    }



//    @Test
//    @DisplayName("회원 삭제 test")
//    fun memberDeleteTest() {
//        //given
//        var memberIds: MutableList<Long> = mutableListOf()
//
//        memberTest1?.let { memberIds.add(it.memberId) }
//
//        var deleteMembersDTO = DeleteMembersDTO(
//            memberIds
//        )
//
//        //when
//        val deleteMembers = adminMemberService.deleteMembers(deleteMembersDTO)
//
//        //then
//        assertThat(deleteMembers.status).isEqualTo(setDeleteSuccessMembersResultDTO().status)
//        assertThat(deleteMembers.message).isEqualTo(setDeleteSuccessMembersResultDTO().message)
//        assertThat(memberTest1?.memberStatus).isEqualTo(false)
//    }
//
//    @Test
//    @DisplayName("존재하지 않는 회원 삭제시 예외 test")
//    fun memberDeleteExceptionTest() {
//        //given
//        var memberIds: MutableList<Long> = mutableListOf()
//
//        memberIds.add(10L)
//
//        var deleteMembersDTO = DeleteMembersDTO(
//            memberIds
//        )
//
//        //when
//        val deleteMembers = adminMemberService.deleteMembers(deleteMembersDTO)
//
//        //then
//        assertThat(deleteMembers.status).isEqualTo(setDeleteFailMembersResultDTO().status)
//        assertThat(deleteMembers.message).isEqualTo(setDeleteFailMembersResultDTO().message)
//        assertThat(memberTest1?.memberStatus).isEqualTo(true)
//    }
}