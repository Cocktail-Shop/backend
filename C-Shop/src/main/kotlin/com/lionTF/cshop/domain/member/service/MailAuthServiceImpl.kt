package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.AuthNumberRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.AuthNumberVerifyRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.MailDTO
import com.lionTF.cshop.domain.member.service.memberinterface.MailAuthService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.NoSuchElementException

@Service
class MailAuthServiceImpl(
    private val redisTemplate: RedisTemplate<String, String>,
    private val javaMailSender: JavaMailSender
) : MailAuthService {

    override fun sendAuthNumber(authNumberDTO: AuthNumberRequestDTO) {
        val authNumber = UUID.randomUUID().toString().replace("-", "").substring(0, 6)

        val saveAuthNumberOperation = redisTemplate.opsForValue()
        saveAuthNumberOperation.set(authNumberDTO.email, authNumber, 4, TimeUnit.MINUTES)

        val mail = MailDTO.toAuthNumberMailDTO(authNumberDTO.email, authNumber)
        mail.sendMail(javaMailSender)
    }

    override fun verifyAuthNumber(authNumberDTO: AuthNumberVerifyRequestDTO): Boolean {
        val getAuthNumberOperation = redisTemplate.opsForValue()
        val existAuthNumber =
            getAuthNumberOperation.get(authNumberDTO.email) ?: throw NoSuchElementException("인증번호 찾을수 없음")

        return authNumberDTO.authNumber == existAuthNumber
    }
}
