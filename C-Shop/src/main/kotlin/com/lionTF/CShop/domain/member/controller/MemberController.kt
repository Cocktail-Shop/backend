package com.lionTF.CShop.domain.member.controller


import com.lionTF.CShop.domain.member.dto.RequestSignUpDTO
import com.lionTF.CShop.domain.member.dto.ResponseDTO
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
    fun signUp(@RequestBody requestSignUpDTO: RequestSignUpDTO):ResponseEntity<ResponseDTO>{
        return memberService.registerMember(requestSignUpDTO)
    }

}