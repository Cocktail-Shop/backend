package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.repository.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*


@Service
class MemberService(val memberAuthRepository: MemberAuthRepository,val cartRepository: CartRepository) {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    //회원가입 로직
    fun registerMember(requestSignUpDTO: RequestSignUpDTO):ResponseEntity<ResponseDTO>{
        val newMember= Member.requestSignUpDTOToMember(requestSignUpDTO)
        val existMember: Optional<Member> = memberAuthRepository.findById(newMember.id!!)

        lateinit var status:HttpStatus
        lateinit var responseDTO: ResponseDTO

        if(existMember.isPresent){
            //기존 아이디 존재하는 경우
            status=HttpStatus.UNAUTHORIZED
            responseDTO= ResponseDTO(status.value(), message = "이미 존재하는 아이디입니다.")
        }else{
            //기존 아이디 존재하지 않는 경우
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart(member =newMember ))
            status=HttpStatus.CREATED
            responseDTO= ResponseDTO(status=status.value(), message = "아이디 생성 성공입니다.")
        }

        return  ResponseEntity(responseDTO,status)
    }


    //아이디 찾기
    fun idInquiry(idInquiryDTO: RequestIdInquiryDTO):ResponseEntity<Any?>{
        val existMember=memberAuthRepository.findByMemberNameAndPhoneNumber(idInquiryDTO.memberName,idInquiryDTO.phoneNumber)

        if(existMember.isPresent){
            val status=HttpStatus.OK
            val responseDTO:ResponseIdInquiryDTO= ResponseIdInquiryDTO.memberToResponseIdInquiryDTO(
                status.value(),
                "회원아이디를 찾았습니다.",
                existMember.get().id
            )

            return ResponseEntity(responseDTO,status)
        }else{
            val status=HttpStatus.UNAUTHORIZED
            val responseDTO= ResponseDTO(status.value(),"존재하지않는 회원입니다.")

            return ResponseEntity(responseDTO,status)
        }


    }
    //비밀번호 찾기
    fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseEntity<ResponseDTO>{
        val existMember=memberAuthRepository.findByIdAndPhoneNumber(passwordInquiryDTO.id,passwordInquiryDTO.phoneNumber)

        lateinit var status:HttpStatus
        lateinit var responseDTO: ResponseDTO

        if(existMember.isPresent){
            status=HttpStatus.OK
            responseDTO= ResponseDTO(status.value(),"비밀번호를 찾았습니다.")
        }else{
            status=HttpStatus.UNAUTHORIZED
            responseDTO= ResponseDTO(status.value(),"존재하지 않는 회원입니다.")
        }

        return ResponseEntity(responseDTO,status)
    }

    //마이페이지 정보 조회
    fun getMyPageInfo(memberId: Long?): ResponseMyPageDTO {
        val member = memberAuthRepository.findByMemberId(memberId).get()
        return ResponseMyPageDTO.memberToResponseMyPageDTO(member)
    }

    //마이페이지 정보  수정
    fun updateMyPageInfo(memberId:Long?,requestUpdateMyPageDTO: RequestUpdateMyPageDTO):ResponseEntity<ResponseDTO>{
        val existMember=memberAuthRepository.findByMemberId(memberId).get()

        val existInfo=memberAuthRepository.findById(requestUpdateMyPageDTO.id)
        val canUpdate=!(existInfo.isPresent && existInfo.get().memberId!=memberId)//이미 존재하는 아이디면서 본인 아이디가 아닌 케이스가 아닌 케이스

        lateinit var status:HttpStatus
        lateinit var responseDTO: ResponseDTO

        when(canUpdate){
            true->{
                existMember.updateMember(requestUpdateMyPageDTO)
                memberAuthRepository.save(existMember)
                status=HttpStatus.CREATED
                responseDTO= ResponseDTO(status.value(),"마이페이지 정보가 성공적으로 수정되었습니다.")
            }
            false -> {
                status=HttpStatus.BAD_REQUEST
                responseDTO=ResponseDTO(status.value(),"이미 사용중인 아이디입니다.")
            }
        }
        return ResponseEntity(responseDTO,status)
    }

    fun updatePassword(memberId:Long?,requestUpdatePasswordDTO: RequestUpdatePasswordDTO):ResponseEntity<ResponseDTO>{
        val existMember=memberAuthRepository.findByMemberId(memberId).get()

        val pastPassword=requestUpdatePasswordDTO.pastPassword
        val newPassword=requestUpdatePasswordDTO.newPassword

        val isMatchExistPassword = passwordEncoder.matches(pastPassword,existMember.password)
        val isPastSameNewPassword= pastPassword==newPassword

        val canUpdate=isMatchExistPassword&&!isPastSameNewPassword

        lateinit var status:HttpStatus
        lateinit var responseDTO: ResponseDTO

        when(canUpdate){
            true->{
                existMember.password=passwordEncoder.encode(newPassword)
                memberAuthRepository.save(existMember)
                status=HttpStatus.CREATED
                responseDTO= ResponseDTO(status.value(),"비밀번호가 성공적으로 수정되었습니다.")
            }
            false->{
                status=HttpStatus.UNAUTHORIZED
                responseDTO=ResponseDTO(status.value(),"비밀번호 변경이 불가능합니다. 기존 비밀번호와 새 비밀번호을 확인해주세요.")
            }
        }

        return ResponseEntity(responseDTO,status)
    }
}