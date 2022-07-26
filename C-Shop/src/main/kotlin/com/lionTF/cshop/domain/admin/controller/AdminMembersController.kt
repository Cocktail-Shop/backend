package com.lionTF.cshop.domain.admin.controller

import com.lionTF.cshop.domain.admin.service.admininterface.AdminMemberService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller

@Controller
@RequestMapping("/admins")
class AdminMembersController(
    private val adminMemberService: AdminMemberService,
) {

    // 회원 전체 조회
    @GetMapping("members")
    fun getAllMembers(pageable: Pageable, model: Model): String {
        model.addAttribute("members", adminMemberService.getAllMembers(pageable))
        return "admins/member/getAllMember"
    }


    // 회원 ID or 회원 이름으로 회원 검색
    @GetMapping("search/members")
    fun findMembers(
        @RequestParam("keyword") keyword: String,
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("searchMembers", adminMemberService.findMembers(keyword, pageable))
        return "admins/member/getSearchMember"
    }

    // 한명의 회원 삭제
    @DeleteMapping("members/{memberId}")
    fun deleteOneMember(@PathVariable("memberId") memberId: Long, model: Model): String {
        model.addAttribute("result", adminMemberService.deleteOneMember(memberId))
        return "global/message"
    }
}
