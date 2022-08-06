package com.lionTF.CShop.domain.member.controller


import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/members")
class MemberController(private val memberService: MemberService) {

    @GetMapping("/success")
    fun successTest(): String {
        return "success"
   }

    @PostMapping("/signup")
    fun signUp(@RequestBody requestSignUpDTO: RequestSignUpDTO):ResponseEntity<ResponseDTO>{
        return memberService.registerMember(requestSignUpDTO)
    }

    //아이디 찾기
    @PostMapping("/idInquiry")
    fun idInquiry(@RequestBody requestIdInquiryDTO: RequestIdInquiryDTO):ResponseEntity<Any?>{
        return memberService.idInquiry(requestIdInquiryDTO)
    }

    @PostMapping("/passwordInquiry")
    fun passwordInquiry(@RequestBody requestPasswordInquiryDTO: RequestPasswordInquiryDTO):ResponseEntity<ResponseDTO>{
        return memberService.passwordInquiry(requestPasswordInquiryDTO)
    }
    //마이페이지
    @GetMapping("")
    fun getMyPageInfo(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?): ResponseMyPageDTO {
        return memberService.getMyPageInfo(authMemberDTO?.memberId)
    }
}