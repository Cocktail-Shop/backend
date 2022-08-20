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
        val existMember: Optional<Member> = memberAuthRepository.findById(newMember.id)

        return if (existMember.isPresent) {
            ResponseDTO.toFailedSignUpResponseDTO()
        } else {
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart(member = newMember))
            ResponseDTO.toSuccessSignUpResponseDTO()
        }

    }


    override fun idInquiry(idInquiryDTO: RequestIdInquiryDTO): Any? {
        val existMember = memberAuthRepository.findByMemberNameAndEmail(idInquiryDTO.memberName, idInquiryDTO.email)

        return if (existMember.isPresent) {
            val member = existMember.get()
            if (member.memberStatus) {
                if (member.fromSocial) {
                    ResponseDTO.toSocialIdInquiryResponseDTO()
                } else ResponseIdInquiryDTO.toSuccessResponseIdInquiryDTO(member)
            } else {
                ResponseDTO.toDeletedIdInquiryResponseDTO()
            }
        } else {
            ResponseDTO.toFailedIdInquiryResponseDTO()
        }
    }


    @Transactional
    override fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO): ResponseDTO {
        val member = memberAuthRepository.findByIdAndEmail(passwordInquiryDTO.id, passwordInquiryDTO.email)

        return if (member.isPresent) {
            if (member.get().memberStatus) {
                if (member.get().fromSocial) {
                    ResponseDTO.toSocialPasswordInquiryResponseDTO()
                } else {
                    val tempPw = UUID.randomUUID().toString().replace("-", "").substring(0, 8)

                    val existMember = member.get()
                    existMember.password = passwordEncoder.encode(tempPw)

                    val mail = MailDTO.toPasswordInquiryMailDTO(existMember.id, existMember.email, tempPw)
                    mail.sendMail(javaMailSender)
                    ResponseDTO.toSuccessPasswordInquiryResponseDTO()
                }
            } else {
                ResponseDTO.toDeletedPasswordInquiryResponseDTO()
            }
        } else {
            ResponseDTO.toFailedPasswordInquiryResponseDTO()
        }
    }

    @Transactional
    override fun setPreMemberInfo(memberId: Long, requestPreMemberInfoDTO: RequestPreMemberInfoDTO): ResponseDTO {
        val existMember = memberAuthRepository.findByMemberId(memberId).orElseThrow()
        existMember.setPreMemberInfo(requestPreMemberInfoDTO)

        return ResponseDTO.toSuccessSetPreMemberInfoResponseDTO()
    }
}
