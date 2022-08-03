package com.lionTF.CShop.domain.member.controller

import com.lionTF.CShop.domain.member.dto.RequestSignUpDTO
import com.lionTF.CShop.domain.member.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/members")
class MemberController(private val memberService: MemberService) {

    @GetMapping("/success")
    fun successTest(): String {
        return "success"
   }

}