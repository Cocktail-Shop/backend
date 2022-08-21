package com.lionTF.cshop.domain.member.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.lionTF.cshop.domain.member.controller.dto.RequestUpdateMyPageDTO
import com.lionTF.cshop.domain.member.controller.dto.PasswordUpdateRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.MemberResponseDTO
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.models.MemberRole
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/*
    테스트 동작 방식
    @WithMockCustomUser
    - 팩토리 메서드를 통해 custom Autentication을 생성하고 Security context에 넣어주는 작업해주는 어노테이션
    - 로그인 인증이 필요한 API를 테스트할 때 사용한다.
 */

@SpringBootTest
@AutoConfigureMockMvc

class MyPageTests {

    @Autowired
    private lateinit var repository: MemberAuthRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    //마이페이지 정보 조회 테스트코드
    @Test
    @DisplayName("MyPage Success Test")
    @WithMockCustomUser
    fun getMyPageInfoTest(){
        mockMvc.perform(get("/members")).andExpect(status().isOk).andDo { println() }
    }

    @Test
    @DisplayName("MyPage Update Success Test")
    @WithMockCustomUser
    fun updateMyPageInfoSuccessTest(){
        val requestBody=RequestUpdateMyPageDTO(
            "test",
            address = "서울시 동작구 상도동", detailAddress = "XX빌딩 103호")
        val content: String = objectMapper.writeValueAsString(requestBody)
        mockMvc.perform(
            put("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .flashAttr("requestUpdateMyPageDTO",requestBody))
            .andDo { println() }
            .andExpect(model().attribute("result",MemberResponseDTO.toSuccessMyPageUpdateResponseDTO()))
    }

    @Test
    @DisplayName("MyPage Update Failed Test")
    @WithMockCustomUser
    fun updateMyPageInfoFailTest(){
        insertUser()
        val requestBody=RequestUpdateMyPageDTO(
            "existUser",
            address = "서울시 동작구 상도동",
            detailAddress = "XX빌딩 103호"
        )

        val content: String = objectMapper.writeValueAsString(requestBody)
        mockMvc.perform(
            put("/members")
                .accept(APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .flashAttr("requestUpdateMyPageDTO",requestBody))
            .andExpect(model().attribute("result",MemberResponseDTO.toFailedMyPageUpdateResponseDTO()))
    }

    @Test
    @DisplayName("MyPage Password Update Success Test")
    @WithMockCustomUser
    fun updatePasswordSuccessTest(){
        val requestBody:PasswordUpdateRequestDTO=PasswordUpdateRequestDTO(pastPassword = "test123", newPassword = "1234")

        val content: String = objectMapper.writeValueAsString(requestBody)
        mockMvc.perform(
            put("/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .flashAttr("requestUpdatePasswordDTO",requestBody))
            .andExpect(model().attribute("result",MemberResponseDTO.toSuccessPasswordUpdateResponseDTO()))
    }
    @Test
    @DisplayName("MyPage Password Update Failed when wrong Password Test")
    @WithMockCustomUser
    fun updatePasswordFailWhenWrongPasswordTest(){
        val requestBody=PasswordUpdateRequestDTO("wrongPassword","1234")

        val content: String = objectMapper.writeValueAsString(requestBody)
        mockMvc.perform(
            put("/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .flashAttr("requestUpdatePasswordDTO",requestBody))
            .andDo { println() }
            .andExpect(model().attribute("result",MemberResponseDTO.toFailedPasswordUpdateResponseDTO()))
    }

    @Test
    @DisplayName("MyPage Password Update Failed Test")
    @WithMockCustomUser
    fun updatePasswordFailWhenSamePasswordTest(){
        val requestBody=PasswordUpdateRequestDTO("test123","test123")

        val content: String = objectMapper.writeValueAsString(requestBody)
        mockMvc.perform(
            put("/members/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .flashAttr("requestUpdatePasswordDTO",requestBody))
            .andDo { println() }
            .andExpect(model().attribute("result",MemberResponseDTO.toFailedPasswordUpdateResponseDTO()))
    }

    fun insertUser(){
        val member: Member = Member(
            id = "existUser",
            password= passwordEncoder.encode("tempPW"),
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동",
            detailAddress = "XX빌딩 103호"
        )
        member.role=MemberRole.MEMBER
        repository.save(member)
    }
}






