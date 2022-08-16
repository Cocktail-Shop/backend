package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.MailDTO
import com.lionTF.cshop.domain.member.controller.dto.RequestAuthNumberDTO
import com.lionTF.cshop.domain.member.controller.dto.RequestVerifyAuthNumberDTO
import com.lionTF.cshop.domain.member.service.memberinterface.MailAuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class MailAuthServiceImpl : MailAuthService {

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>

    @Autowired
    private lateinit var javaMailService: JavaMailSender

    //인증관련
    override fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO){
        //인증번호 생성
        var authNumber = UUID.randomUUID().toString().replace("-", "")
        authNumber = authNumber.substring(0, 6)

        //인증번호 저장
        val saveAuthNumberOperation=redisTemplate.opsForValue()
        saveAuthNumberOperation.set(authNumberDTO.email,authNumber,4, TimeUnit.MINUTES)

        //메일 보내기
        val mail = MailDTO.toAuthNumberMailDTO(authNumberDTO.email,authNumber)
        mail.sendMail(javaMailService)
    }

    override fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO):Boolean{
        //인증번호 검증
        val getAuthNumberOperation=redisTemplate.opsForValue()
        val existAuthNumber=getAuthNumberOperation.get(authNumberDTO.email)

        return authNumberDTO.authNumber==existAuthNumber
    }

}