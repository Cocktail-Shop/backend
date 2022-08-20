package com.lionTF.cshop.domain.member.service

import com.lionTF.cshop.domain.member.controller.dto.*
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import com.lionTF.cshop.domain.member.service.memberinterface.MemberService
import com.lionTF.cshop.domain.shop.models.Cart
import com.lionTF.cshop.domain.shop.repository.CartRepository
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import kotlin.NoSuchElementException


@Service
class MemberServiceImpl(
    private val memberAuthRepository: MemberAuthRepository,
    private val cartRepository: CartRepository,
    private val passwordEncoder: PasswordEncoder,
    private val javaMailSender: JavaMailSender
) : MemberService {

    @Transactional
    override fun registerMember(requestSignUpDTO: RequestSignUpDTO): ResponseDTO {
        requestSignUpDTO.encoding(passwordEncoder)

        val newMember = Member.fromRequestSignUpDTO(requestSignUpDTO)
        val existMember = memberAuthRepository.findById(newMember.id)

        return if (existMember != null) {
            ResponseDTO.toFailedSignUpResponseDTO()
        } else {
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart.fromMember(newMember))
            ResponseDTO.toSuccessSignUpResponseDTO()
        }

    }


    override fun requestIdInquiry(idInquiryDTO: RequestIdInquiryDTO): Any? {
        return memberAuthRepository.findByMemberNameAndEmail(idInquiryDTO.memberName, idInquiryDTO.email)?.let {
            if (it.memberStatus) {
                when (it.fromSocial) {
                    true -> ResponseDTO.toSocialIdInquiryResponseDTO()
                    else -> ResponseIdInquiryDTO.toSuccessResponseIdInquiryDTO(it)
                }
            } else {
                ResponseDTO.toDeletedIdInquiryResponseDTO()
            }
        } ?: ResponseDTO.toFailedIdInquiryResponseDTO()
    }


    @Transactional
    override fun requestPasswordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO): ResponseDTO {
        return memberAuthRepository.findByIdAndEmail(passwordInquiryDTO.id, passwordInquiryDTO.email)?.let {
            if (it.memberStatus) {
                when (it.fromSocial) {
                    true -> ResponseDTO.toSocialPasswordInquiryResponseDTO()
                    else -> {
                        val tempPw = UUID.randomUUID().toString().replace("-", "").substring(0, 8)

                        it.password = passwordEncoder.encode(tempPw)

                        val mail = MailDTO.toPasswordInquiryMailDTO(it.id, it.email, tempPw)
                        mail.sendMail(javaMailSender)
                        return ResponseDTO.toSuccessPasswordInquiryResponseDTO()
                    }
                }
            } else {
                ResponseDTO.toDeletedPasswordInquiryResponseDTO()
            }
        } ?: ResponseDTO.toFailedPasswordInquiryResponseDTO()
    }

    @Transactional
    override fun setPreMemberInfo(memberId: Long, requestPreMemberInfoDTO: RequestPreMemberInfoDTO): ResponseDTO {
        return memberAuthRepository.findByMemberId(memberId)?.let {
            it.setPreMemberInfo(requestPreMemberInfoDTO)
            return ResponseDTO.toSuccessSetPreMemberInfoResponseDTO()
        } ?: throw NoSuchElementException("해당 회원 정보 찾을 수 없음")
    }
}
