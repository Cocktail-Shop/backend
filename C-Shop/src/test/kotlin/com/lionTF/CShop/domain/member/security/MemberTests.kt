package com.lionTF.CShop.domain.member.security

import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*
import javax.persistence.*
import kotlin.coroutines.coroutineContext

/**
 * 목표: 로그인 성공, 실패 테스트
 * 실행 전
 * - insertMember() 실행하여 testUser insert
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


    @Test
    @DisplayName("최초 실행시 testUser 생성")
    fun insertMember(){
        val id="TestUser"
        val password="1111"

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
        val id="TestUser"
        val password="1111"
        mockMvc.perform(formLogin().user(id).password(password))
            .andExpect(authenticated())
    }

    @Test
    @DisplayName("Login Fail Test")
    fun loginFailedTest(){
        val id="TestUser"
        val wrongPassword="1112"

        mockMvc.perform(formLogin().user(id).password(wrongPassword))//잘못된 패스워드 입력
            .andExpect(unauthenticated())
    }

}