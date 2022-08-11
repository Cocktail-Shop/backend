package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.repository.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class MemberService(val memberAuthRepository: MemberAuthRepository,val cartRepository: CartRepository) {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

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
        val existMember=memberAuthRepository.findByMemberNameAndPhoneNumber(idInquiryDTO.memberName,idInquiryDTO.phoneNumber)

        return if(existMember.isPresent) {
            ResponseIdInquiryDTO.memberToSuccessResponseIdInquiryDTO(existMember.get())
        }else{
            ResponseDTO.toFailedIdInquiryResponseDTO()
        }


    }
    //비밀번호 찾기
    fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseDTO{
        val existMember=memberAuthRepository.findByIdAndPhoneNumber(passwordInquiryDTO.id,passwordInquiryDTO.phoneNumber)

        return if(existMember.isPresent){
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

    fun deleteMember(authMemberDTO: AuthMemberDTO?):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(authMemberDTO?.memberId).orElseThrow()
        existMember.deleteMember()
        return ResponseDTO.toDeleteMemberResponseDTO()
    }

}