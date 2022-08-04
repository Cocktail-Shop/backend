package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.dto.IdInquiryDTO
import com.lionTF.CShop.domain.member.dto.ResponseDTO
import com.lionTF.CShop.domain.member.dto.SignUpDTO
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.repository.CartRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*


@Service
class MemberService(val memberAuthRepository: MemberAuthRepository,val cartRepository: CartRepository) {


    //회원가입 로직
    fun registerMember(requestSignUpDTO: SignUpDTO.RequestDTO):ResponseEntity<ResponseDTO>{
        val newMember= Member.requestSignUpDTOToMember(requestSignUpDTO)
        val existMember: Optional<Member> = memberAuthRepository.findById(newMember.id!!)

        lateinit var status:HttpStatus
        lateinit var responseDTO: ResponseDTO

        if(existMember.isPresent){
            //기존 아이디 존재하는 경우
            status=HttpStatus.UNAUTHORIZED
            responseDTO=ResponseDTO(status.value(), message = "이미 존재하는 아이디입니다.")
        }else{
            //기존 아이디 존재하지 않는 경우
            memberAuthRepository.save(newMember)
            cartRepository.save(Cart(member =newMember ))
            status=HttpStatus.CREATED
            responseDTO=ResponseDTO(status=status.value(), message = "아이디 생성 성공입니다.")
        }

        return  ResponseEntity(responseDTO,status)
    }


    //아이디 찾기
    fun idInquiry(idInquiryDTO: IdInquiryDTO.RequestDTO):ResponseEntity<Any?>{
        //이름, 비밀번호 둘다 일치하는거 찾아옴
        val existMember=memberAuthRepository.findByMemberNameAndPhoneNumber(idInquiryDTO.memberName,idInquiryDTO.phoneNumber)


        if(existMember.isPresent){
            val status=HttpStatus.OK
            val responseDTO=IdInquiryDTO.ResponseDTO(
                status.value(),
                "회원아이디를 찾았습니다.",
                IdInquiryDTO.ResultDTO(existMember.get().id)
            )

            return ResponseEntity(responseDTO,status)
        }else{
            val status=HttpStatus.UNAUTHORIZED
            val responseDTO=ResponseDTO(status.value(),"존재하지않는 회원입니다.")

            return ResponseEntity(responseDTO,status)
        }


    }

}