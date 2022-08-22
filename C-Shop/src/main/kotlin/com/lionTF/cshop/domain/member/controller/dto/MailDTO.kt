package com.lionTF.cshop.domain.member.controller.dto

import com.lionTF.cshop.domain.member.models.Member
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

data class MailDTO(
    val toAddress: String,
    val title: String,
    val message: String,
    val fromAddress: String
) {

    fun sendMail(javaMailService: JavaMailSender) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(this.toAddress)
        mailMessage.setSubject(this.title)
        mailMessage.setText(this.message)
        mailMessage.setFrom(this.fromAddress)
        mailMessage.setReplyTo(this.fromAddress)

        javaMailService.send(mailMessage)
    }

    companion object {
        fun toPasswordInquiryMailDTO(member: Member, tempPw: String): MailDTO {
            val title = "${member.id} 님 임시 비밀번호 안내 이메일 입니다."
            val message = """
            안녕하세요. ${member.id} 님 임시 비밀번호 안내 메일입니다.
            회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주시길 바랍니다. 
            임시 비밀번호 : $tempPw
            """.trimIndent()
            val fromAddress = "cshop1234@naver.com"

            return MailDTO(
                member.email,
                title,
                message,
                fromAddress
            )
        }

        fun toAuthNumberMailDTO(authNumberDTO: AuthNumberRequestDTO, authPw: String): MailDTO {
            val title = "C-Shop 이메일 인증 인증번호 이메일 입니다."
            val message = """
            안녕하세요. C-shop 이메일 인증 인증번호 안내 메일입니다.
            회원님의 인증번호는 아래와 같습니다. 
            인증번호 : $authPw
            """.trimIndent()
            val fromAddress = "cshop1234@naver.com"

            return MailDTO(
                authNumberDTO.email,
                title,
                message,
                fromAddress
            )
        }
    }
}
