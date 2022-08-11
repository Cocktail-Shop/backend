package com.lionTF.CShop.domain.member.security


import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.member.service.MemberService
import org.assertj.core.api.Assertions.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


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

    //test용 id, 패스워드
    val id="test"
    val password="test123"

    @BeforeEach
    fun insertMember(){
        val member: Member = Member(
            id = id,
            password= passwordEncoder.encode(password),
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동",
            email="test@naver.com",
            detailAddress = "XX빌딩 103호"
        )
        member.role=MemberRole.MEMBER
        repository.save(member)
    }

    @Test
    @DisplayName("Login Success Test")
    fun loginSuccessTest(){
        mockMvc.perform(formLogin().user(id).password(password))
            .andExpect(authenticated())
    }

    @Test
    @DisplayName("Login Fail Test")
    fun loginFailedTest(){
        val wrongPassword="1112"
        mockMvc.perform(formLogin().user(id).password(wrongPassword))//잘못된 패스워드 입력
            .andExpect(unauthenticated())
    }

    @Test
    @DisplayName("Signup Success Test")
    fun signUpSuccessTest(){
        val requestSignUpDTO: RequestSignUpDTO = RequestSignUpDTO(
            id="newUser",
            password = "test123",
            phoneNumber = "01012341234",
            memberName = "사용자",
            email="test@naver.com",
            address = "서울시 동작구 상도동",
            detailAddress = "XX빌딩 103호"
        )

        //when
        val signUpResult=memberService.registerMember(requestSignUpDTO)

        //then
        assertThat(signUpResult.status).isEqualTo(ResponseDTO.toSuccessSignUpResponseDTO().status)
    }

    @Test
    @DisplayName("Signup Fail Test")
    fun signUpFailTest(){
        val requestSignUpDTO: RequestSignUpDTO = RequestSignUpDTO(
            id="test",
            password = "test123",
            phoneNumber = "01012341234",
            memberName = "사용자",
            email="test@naver.com",
            address = "서울시 동작구 상도동",
            detailAddress = "XX빌딩 103호"
        )

        //when : 같은 정보갖는 회원 두번 삽입
        val signUpResult=memberService.registerMember(requestSignUpDTO)
        //then
        assertThat(signUpResult.status).isEqualTo(ResponseDTO.toFailedSignUpResponseDTO().status)
    }

    @Test
    @DisplayName("IdInquiry Success Test")
    fun idInquirySuccessTest(){
        val requestIdInquiryDTO= RequestIdInquiryDTO(
            "사용자",
            "01012341234"
        )

        //when
        val idInquiryResult=memberService.idInquiry(requestIdInquiryDTO)
        //then : status code, response type이 성공 형태인지 판단 + 찾아온 id가 사용자의 id인지 검사
        assertThat(idInquiryResult, instanceOf(ResponseIdInquiryDTO::class.java))
        assertThat((idInquiryResult as ResponseIdInquiryDTO).result.id).isEqualTo(id)
    }

    @Test
    @DisplayName("IdInquiry Fail when wrong memberName Test")
    fun idInquiryWhenWrongMemberName(){
        val requestIdInquiryDTO= RequestIdInquiryDTO(
            "없는사용자",
            "01012341234"
        )

        //when : 존재하지 않는 사용자 정보를 줌
        val idInquiryResult=memberService.idInquiry(requestIdInquiryDTO)

        //then : status code, response type이 실패 형태인지 판단

        assertThat(idInquiryResult, instanceOf(ResponseDTO::class.java))
        assertThat((idInquiryResult as ResponseDTO).status).isEqualTo(ResponseDTO.toFailedIdInquiryResponseDTO().status)
    }

    @Test
    @DisplayName("IdInquiry Fail when wrong phoneNumber Test")
    fun idInquiryWhenWrongPhoneNumber(){
        val requestIdInquiryDTO= RequestIdInquiryDTO(
            "사용자",
            "01011111111"
        )

        //when : 사용자는 존재하지만 전화번호 잘못된 경우
        val idInquiryResult=memberService.idInquiry(requestIdInquiryDTO)

        //then : status code, response type이 실패 형태인지 판단
        assertThat(idInquiryResult, instanceOf(ResponseDTO::class.java))
        assertThat((idInquiryResult as ResponseDTO).status).isEqualTo(ResponseDTO.toFailedIdInquiryResponseDTO().status)
    }


    @Test
    @DisplayName("passwordInquiry Success Test")
    fun passwordInquirySuccessTest(){
        val requestPasswordInquiryDTO= RequestPasswordInquiryDTO(
            "test",
            "test@naver.com"
        )


        //when
        val passwordInquiryResult=memberService.passwordInquiry(requestPasswordInquiryDTO)

        //then
        assertThat(passwordInquiryResult.status).isEqualTo(ResponseDTO.toSuccessPasswordInquiryResponseDTO().status)
    }

    @Test
    @DisplayName("passwordInquiry Fail when wrong memberName Test")
    fun passwordInquiryWhenWrongMemberName(){
        val requestPasswordInquiryDTO= RequestPasswordInquiryDTO(
            "없는사용자",
            "test@naver.com"
        )

        //when : 존재하지 않는 사  용자 정보를 줌
        val passwordInquiryResult=memberService.passwordInquiry(requestPasswordInquiryDTO)

        //then
        assertThat(passwordInquiryResult.status).isEqualTo(ResponseDTO.toFailedPasswordInquiryResponseDTO().status)
    }

    @Test
    @DisplayName("passwordInquiry Fail when wrong phoneNumber Test")
    fun passwordInquiryWhenWrongPhoneNumber(){
        val requestPasswordInquiryDTO= RequestPasswordInquiryDTO(
            "test",
            "noUser@naver.com"
        )

        //when : 사용자는 존재하지만 전화번호 잘못된 경우
        val passwordInquiryResult=memberService.passwordInquiry(requestPasswordInquiryDTO)

        //then : status code
        assertThat(passwordInquiryResult.status).isEqualTo(ResponseDTO.toFailedPasswordInquiryResponseDTO().status)
    }

    @Test
    @DisplayName("Logout Test")
    @WithMockCustomUser
    fun logoutPassword(){
        mockMvc.perform(post("/members/logout")
            .with(csrf()))
            .andExpect(unauthenticated())
    }

}