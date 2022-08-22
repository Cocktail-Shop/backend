package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.service.admininterface.AdminMemberService
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.shop.repository.MemberRepository
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
internal class AdminMemberServiceTest {

    @Autowired
    private val adminMemberService: AdminMemberService? = null

    @Autowired
    private val memberRepository: MemberRepository? = null

    private var memberTest1: Member? = null
    private var memberTest2: Member? = null
    private var memberTest3: Member? = null

    @BeforeEach
    fun init() {
        val member1 = Member(
            memberName = "test1",
            address = "address-test1",
            phoneNumber = "phone-test1",
            id = "id-test1"
        )
        memberTest1 = memberRepository?.save(member1)

        val member2 = Member(
            memberName = "test2",
            address = "address-test2",
            phoneNumber = "phone-test2",
            id = "id-test2"
        )
        memberTest2 = memberRepository?.save(member2)

        val member3 = Member(
            memberName = "test3",
            address = "address-test3",
            phoneNumber = "phone-test3",
            id = "id-test3"
        )
        memberTest3 = memberRepository?.save(member3)
    }


    @Test
    @DisplayName("한명의 회원 삭제 test")
    fun deleteOneMemberTest() {
        //given
        val memberId: Long? = memberTest1?.memberId

        //when
        val deleteOneMember = memberId?.let { adminMemberService?.deleteOneMember(it) }

        //then
        assertThat(deleteOneMember?.httpStatus).isEqualTo(AdminResponseDTO.toSuccessDeleteMemberResponseDTO().httpStatus)
        assertThat(deleteOneMember?.message).isEqualTo(AdminResponseDTO.toSuccessDeleteMemberResponseDTO().message)
        assertThat(memberTest1?.memberStatus).isEqualTo(false)
        println("deleteOneMember = ${deleteOneMember?.message}")
    }

    @Test
    @DisplayName("한명의 회원 삭제 예외 test")
    fun deleteOneMemberExceptionTest() {
        //given
        val memberId = 98L

        //when
        val deleteOneMember = adminMemberService?.deleteOneMember(memberId)

        //then
        assertThat(deleteOneMember?.httpStatus).isEqualTo(AdminResponseDTO.toFailDeleteMemberResponseDTO().httpStatus)
        assertThat(deleteOneMember?.message).isEqualTo(AdminResponseDTO.toFailDeleteMemberResponseDTO().message)
        assertThat(memberTest1?.memberStatus).isEqualTo(true)
        println("deleteOneMember = ${deleteOneMember?.message}")
    }

    private fun generatePageable(page: Int = 0, pageSize: Int = 2): PageRequest = PageRequest.of(page, pageSize)


    @Test
    @DisplayName("회원 전체 조회 test")
    fun getAllMembersTest() {
        //given
        val pageable = generatePageable()

        //when
        val allMembers = adminMemberService?.getAllMembers(pageable)

        val countMember = memberRepository?.count()

        //then
        assertThat(allMembers?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allMembers?.message).isEqualTo("회원 조회를 성공했습니다.")
        assertThat(allMembers?.keyword).isEqualTo("")
        assertThat(allMembers?.result?.totalElements).isEqualTo(countMember)
        assertThat(allMembers?.result?.totalPages).isEqualTo(2)
    }

    @Test
    @DisplayName("회원 ID로 회원 검색 test")
    fun findMembers() {
        //given
        val pageable = generatePageable()
        val keyword = "test"

        //when
        val findMembers = adminMemberService?.findMembers(keyword, pageable)

        //then
        assertThat(findMembers?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(findMembers?.message).isEqualTo("회원 조회를 성공했습니다.")
        assertThat(findMembers?.keyword).isEqualTo(keyword)
        assertThat(findMembers?.result?.totalElements).isEqualTo(4)
        assertThat(findMembers?.result?.totalPages).isEqualTo(2)
    }
}
