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
    override fun registerMember(signUpRequestDTO: SignUpRequestDTO): MemberResponseDTO {
        signUpRequestDTO.encoding(passwordEncoder)

        val newMember = Member.fromRequestSignUpDTO(signUpRequestDTO)
        val existMember = memberAuthRepository.findById(newMember.id)

        return if (existMember != null) {
            MemberResponseDTO.toFailedSignUpResponseDTO()
        } else {
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart.fromMember(newMember))
            MemberResponseDTO.toSuccessSignUpResponseDTO()
        }
    }


    override fun requestIdInquiry(idInquiryDTO: IdInquiryRequestDTO): Any? {
        return memberAuthRepository.findByMemberNameAndEmail(idInquiryDTO.memberName, idInquiryDTO.email)
            ?.let { existMember ->
                when {
                    !existMember.memberStatus -> MemberResponseDTO.toDeletedIdInquiryResponseDTO()
                    existMember.fromSocial -> MemberResponseDTO.toSocialIdInquiryResponseDTO()
                    else -> IdInquiryResponseDTO.toSuccessIdInquiryResponseDTO(existMember)
                }
            } ?: MemberResponseDTO.toFailedIdInquiryResponseDTO()
    }


    @Transactional
    override fun requestPasswordInquiry(passwordInquiryDTO: PasswordInquiryRequestDTO): MemberResponseDTO {
        return memberAuthRepository.findByIdAndEmail(passwordInquiryDTO.id, passwordInquiryDTO.email)
            ?.let { existMember ->
                when {
                    !existMember.memberStatus -> MemberResponseDTO.toDeletedPasswordInquiryResponseDTO()
                    existMember.fromSocial -> MemberResponseDTO.toSocialPasswordInquiryResponseDTO()
                    else -> {
                        sendMail(existMember)
                        MemberResponseDTO.toSuccessPasswordInquiryResponseDTO()
                    }
                }
            } ?: MemberResponseDTO.toFailedPasswordInquiryResponseDTO()
    }


    private fun sendMail(member: Member) {
        val tempPw = UUID.randomUUID().toString().replace("-", "").substring(0, 8)
        member.updatePassword(tempPw, passwordEncoder)

        val mail = MailDTO.toPasswordInquiryMailDTO(member, tempPw)
        mail.sendMail(javaMailSender)
    }


    @Transactional
    override fun setPreMemberInfo(memberId: Long, preMemberInfoRequestDTO: PreMemberInfoRequestDTO): MemberResponseDTO {
        return memberAuthRepository.findByMemberId(memberId)?.let { existMember ->
            existMember.setPreMemberInfo(preMemberInfoRequestDTO)
            MemberResponseDTO.toSuccessSetPreMemberInfoResponseDTO()
        } ?: throw NoSuchElementException("해당 회원 정보 찾을 수 없음")
    }
}
