package com.lionTF.CShop.domain.member.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.lionTF.CShop.domain.member.controller.dto.RequestUpdateMyPageDTO
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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
        mockMvc.perform(get("/members")).andExpect(status().isOk()).andDo { println() }
    }

    @Test
    @DisplayName("MyPage Update Success Test")
    @WithMockCustomUser
    fun updateMyPageInfoSuccessTest(){
        val requestBody=RequestUpdateMyPageDTO(
            "test",
            address = "서울시 동작구 상도동 XX빌딩 103호")
        val content: String = objectMapper.writeValueAsString(requestBody)
        println(content)
        mockMvc.perform(
            put("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andDo { println() }
            .andExpect(status().isCreated)
    }

    @Test
    @DisplayName("MyPage Update Failed Test")
    @WithMockCustomUser
    fun updateMyPageInfoFailTest(){
        insertUser()
        val requestBody=RequestUpdateMyPageDTO(
            "existUser",
            address = "서울시 동작구 상도동 XX빌딩 103호")

        val content: String = objectMapper.writeValueAsString(requestBody)
        println(content)
        mockMvc.perform(
            put("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andDo { println() }
            .andExpect(status().isBadRequest)
    }

    fun insertUser(){
        val member: Member = Member(
            id = "existUser",
            password= passwordEncoder.encode("tempPW"),
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동 XX빌딩 103호"
        )
        member.role=MemberRole.MEMBER
        repository.save(member)
    }
}






