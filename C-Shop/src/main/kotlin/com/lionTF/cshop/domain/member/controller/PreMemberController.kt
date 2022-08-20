package com.lionTF.cshop.domain.member.controller

import com.lionTF.cshop.domain.member.controller.dto.AuthMemberDTO
import com.lionTF.cshop.domain.member.controller.dto.PreMemberInfoRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.MemberResponseDTO
import com.lionTF.cshop.domain.member.service.memberinterface.MemberService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/pre-members")
class PreMemberController(private val memberService: MemberService) {

    @GetMapping("/deny")
    fun getPreMemberAccessDeniedPage(model: Model): String {
        model.addAttribute("result", MemberResponseDTO.toPreMemberAccessDeniedResponseDTO())
        return "global/message"
    }

    @GetMapping
    fun getPreMemberPage(model: Model): String {
        model.addAttribute("requestPreMemberInfoDTO", PreMemberInfoRequestDTO.toFormDTO())
        return "members/pre-member-page"
    }

    @PostMapping
    fun setPreMembersInfo(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        preMemberInfoRequestDTO: PreMemberInfoRequestDTO,
        model: Model
    ): String {
        model.addAttribute("result", memberService.setPreMemberInfo(authMemberDTO.memberId, preMemberInfoRequestDTO))
        return "global/message"
    }
}
