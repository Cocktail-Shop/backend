package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.member.service.memberinterface.MemberService
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
class MemberServiceImpl(val memberAuthRepository: MemberAuthRepository,val cartRepository: CartRepository)
    : MemberService {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var javaMailService: JavaMailSender


    //회원가입 로직
    override fun registerMember(requestSignUpDTO: RequestSignUpDTO):ResponseDTO{
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
    override fun idInquiry(idInquiryDTO: RequestIdInquiryDTO):Any?{
        val existMember=memberAuthRepository.findByMemberNameAndEmail(idInquiryDTO.memberName,idInquiryDTO.email)

        return if(existMember.isPresent) {
            ResponseIdInquiryDTO.toSuccessResponseIdInquiryDTO(existMember.get())
        }else{
            ResponseDTO.toFailedIdInquiryResponseDTO()
        }
    }



    //비밀번호 찾기
    override fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseDTO{
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
            val mail=MailDTO.toPasswordInquiryMailDTO(existMember.id,existMember.email,tempPw)
            mail.sendMail(javaMailService)
            ResponseDTO.toSuccessPasswordInquiryResponseDTO()
        }else{
            ResponseDTO.toFailedPasswordInquiryResponseDTO()
        }
    }



}