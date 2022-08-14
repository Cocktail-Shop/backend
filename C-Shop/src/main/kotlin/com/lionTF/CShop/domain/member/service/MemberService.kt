package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.repository.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class MemberService(val memberAuthRepository: MemberAuthRepository,val cartRepository: CartRepository) {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var javaMailService: JavaMailSender

    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, String>
    //회원가입 로직
    fun registerMember(requestSignUpDTO: RequestSignUpDTO):ResponseDTO{
        requestSignUpDTO.encoding()

        val newMember= Member.requestSignUpDTOToMember(requestSignUpDTO)
        val existMember: Optional<Member> = memberAuthRepository.findById(newMember.id)

        return if(existMember.isPresent){
            //기존 아이디 존재하는 경우
            ResponseDTO.toFailedSignUpResponseDTO()
        }else{
            //기존 아이디 존재하지 않는 경우
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart(member =newMember ))
            ResponseDTO.toSuccessSignUpResponseDTO()
        }

    }


    //아이디 찾기
    fun idInquiry(idInquiryDTO: RequestIdInquiryDTO):Any?{
        val existMember=memberAuthRepository.findByMemberNameAndEmail(idInquiryDTO.memberName,idInquiryDTO.email)

        return if(existMember.isPresent) {


            ResponseIdInquiryDTO.memberToSuccessResponseIdInquiryDTO(existMember.get())
        }else{
            ResponseDTO.toFailedIdInquiryResponseDTO()
        }
    }

    //인증관련
    fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO){
        //인증번호 생성
        var authNumber = UUID.randomUUID().toString().replace("-", "")
        authNumber = authNumber.substring(0, 6)

        //인증번호 저장
        val saveAuthNumberOperation=redisTemplate.opsForValue()
        saveAuthNumberOperation.set(authNumberDTO.email,authNumber,4,TimeUnit.MINUTES)

        //메일 보내기
        sendTempPasswordMail(MailDTO.toAuthNumberMailDTO(authNumberDTO.email,authNumber))
    }

    fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO):Boolean{
        //인증번호 검증
        val getAuthNumberOperation=redisTemplate.opsForValue()
        val existAuthNumber=getAuthNumberOperation.get(authNumberDTO.email)

        return authNumberDTO.authNumber==existAuthNumber
    }

    //비밀번호 찾기
    fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseDTO{
        val member=memberAuthRepository.findByIdAndEmail(passwordInquiryDTO.id,passwordInquiryDTO.email)

        return if(member.isPresent){
            //임시 비밀번호 생성
            var tempPw = UUID.randomUUID().toString().replace("-", "")
            tempPw = tempPw.substring(0, 8)

            //임시 비밀번호로 변경
            val existMember=member.get()
            existMember.password=passwordEncoder.encode(tempPw)
            memberAuthRepository.save(existMember)

            //메일보내고 return
            sendTempPasswordMail(MailDTO.toPasswordInquiryMailDTO(existMember.id,existMember.email,tempPw))
            ResponseDTO.toSuccessPasswordInquiryResponseDTO()
        }else{
            ResponseDTO.toFailedPasswordInquiryResponseDTO()
        }
    }

    //마이페이지 정보 조회
    fun getMyPageInfo(memberId: Long?): ResponseMyPageDTO {
        val member = memberAuthRepository.findByMemberId(memberId).orElseThrow()
        return ResponseMyPageDTO.memberToResponseMyPageDTO(member)
    }

    //마이페이지 정보 수정
    fun updateMyPageInfo(memberId:Long?,requestUpdateMyPageDTO: RequestUpdateMyPageDTO):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(memberId).orElseThrow()

        val existInfo=memberAuthRepository.findById(requestUpdateMyPageDTO.id)
        val canUpdate=!(existInfo.isPresent && existInfo.get().memberId!=memberId)//이미 존재하는 아이디면서 본인 아이디가 아닌 케이스가 아닌 케이스


        return if(canUpdate) {
            existMember.updateMember(requestUpdateMyPageDTO)
            memberAuthRepository.save(existMember)
            ResponseDTO.toSuccesUpdateMyPageResponseDTO()
        }else{
            ResponseDTO.toFailedUpdateMyPageResponseDTO()
        }

    }

    fun updatePassword(memberId:Long?,requestUpdatePasswordDTO: RequestUpdatePasswordDTO):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(memberId).orElseThrow()

        val pastPassword=requestUpdatePasswordDTO.pastPassword
        val newPassword=requestUpdatePasswordDTO.newPassword

        val isMatchExistPassword = passwordEncoder.matches(pastPassword,existMember.password)
        val isPastSameNewPassword= pastPassword==newPassword

        val canUpdate=isMatchExistPassword&&!isPastSameNewPassword

        return if(canUpdate){
            existMember.updateMemberPassword(pastPassword,passwordEncoder)
            memberAuthRepository.save(existMember)
            ResponseDTO.toSuccessUpdatePasswordDTO()
        }else{
            ResponseDTO.toFailedUpdatePasswordDTO()
        }
    }

    //회원탈퇴
    fun deleteMember(authMemberDTO: AuthMemberDTO?):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(authMemberDTO?.memberId).orElseThrow()
        existMember.deleteMember()
        return ResponseDTO.toDeleteMemberResponseDTO()
    }

    //메일관련
    fun sendTempPasswordMail(mailDTO: MailDTO){
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(mailDTO.toAddress)
        mailMessage.setSubject(mailDTO.title)
        mailMessage.setText(mailDTO.message)
        mailMessage.setFrom(mailDTO.fromAddress)
        mailMessage.setReplyTo(mailDTO.fromAddress)

        javaMailService.send(mailMessage)
    }


}