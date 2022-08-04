package com.lionTF.CShop.domain.member.security


import com.lionTF.CShop.domain.member.dto.RequestSignUpDTO
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.member.service.MemberService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.web.servlet.MockMvc

/**
 * 목표: 로그인 성공, 실패 테스트, 회원가입 성공, 실패 테스트
 */


@SpringBootTest
@AutoConfigureMockMvc
class MemberTests{
    @Autowired
    private lateinit var repository:MemberAuthRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var memberService: MemberService


    val id="TestUser"
    val password="1111"
    //test용 id, 패스워드
    fun insertMember(){
        val member: Member = Member(
            id = id,
            password= passwordEncoder.encode(password),
            phoneNumber = "",
            memberName = "",
            address = ""
        )
        member.role=MemberRole.MEMBER
        repository.save(member)
    }

    @Test
    @DisplayName("Login Success Test")
    fun loginSuccessTest(){
        insertMember()
        mockMvc.perform(formLogin().user(id).password(password))
            .andExpect(authenticated())
    }

    @Test
    @DisplayName("Login Fail Test")
    fun loginFailedTest(){
        insertMember()
        val wrongPassword="1112"
        mockMvc.perform(formLogin().user(id).password(wrongPassword))//잘못된 패스워드 입력
            .andExpect(unauthenticated())
    }

    @Test
    @DisplayName("Signup Success Test")
    fun signUpSuccessTest(){
        val requestSignUpDTO:RequestSignUpDTO= RequestSignUpDTO(
            id="test",
            password = "test123",
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동 XX빌딩 103호"
        )

        //when
        val signUpResult=memberService.registerMember(requestSignUpDTO)

        //then
        Assertions.assertThat(signUpResult.statusCode).isEqualTo(HttpStatus.CREATED)
        Assertions.assertThat(signUpResult.body!!.status).isEqualTo(HttpStatus.CREATED.value())
    }

    @Test
    @DisplayName("Signup Fail Test")
    fun signUpFailTest(){
        val requestSignUpDTO:RequestSignUpDTO= RequestSignUpDTO(
            id="test",
            password = "test123",
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동 XX빌딩 103호"
        )

        //when : 같은 정보갖는 회원 두번 삽입
        memberService.registerMember(requestSignUpDTO)
        val signUpResult=memberService.registerMember(requestSignUpDTO)
        //then
        Assertions.assertThat(signUpResult.statusCode).isEqualTo(HttpStatus.UNAUTHORIZED)
        Assertions.assertThat(signUpResult.body!!.status).isEqualTo(HttpStatus.UNAUTHORIZED.value())
    }

}