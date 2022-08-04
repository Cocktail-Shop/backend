package com.lionTF.CShop.domain.member.controller


import com.lionTF.CShop.domain.member.dto.IdInquiryDTO
import com.lionTF.CShop.domain.member.dto.PasswordInquiryDTO
import com.lionTF.CShop.domain.member.dto.ResponseDTO
import com.lionTF.CShop.domain.member.dto.SignUpDTO
import com.lionTF.CShop.domain.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@Controller
@RequestMapping("/members")
class MemberController(private val memberService: MemberService) {

    @GetMapping("/success")
    fun successTest(): String {
        return "success"
   }

    @PostMapping("/signup")
    fun signUp(@RequestBody requestSignUpDTO: SignUpDTO.RequestDTO):ResponseEntity<ResponseDTO>{
        return memberService.registerMember(requestSignUpDTO)
    }

    //아이디 찾기
    @PostMapping("/idInquiry")
    fun idInquiry(@RequestBody requestIdInquiryDTO: IdInquiryDTO.RequestDTO):ResponseEntity<Any?>{
        return memberService.idInquiry(requestIdInquiryDTO)
    }

    @PostMapping("/passwordInquir")
    fun passwordInquiry(@RequestBody requestPasswordInquiryDTO: PasswordInquiryDTO.RequestDTO):ResponseEntity<ResponseDTO>{
        return memberService.passwordInquiry(requestPasswordInquiryDTO)
    }

}