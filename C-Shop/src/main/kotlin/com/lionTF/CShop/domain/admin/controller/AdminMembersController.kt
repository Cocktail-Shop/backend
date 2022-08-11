package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.stereotype.Controller

@Controller
@RequestMapping("/admins")
class AdminMembersController(
    private val adminMemberService: AdminMemberService,
) {

    // 회원 전체 조회
    @GetMapping("members")
    fun getAllMembers(
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("members" ,adminMemberService.getAllMembers(pageable))
        return "admins/member/getAllMember"
    }


    // 회원 ID로 회원 검색
    @GetMapping("search/members")
    fun findMembers(
        @RequestParam("keyword") keyword: String,
        @PageableDefault(size = 2) pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("searchMembers", adminMemberService.findMembers(keyword, pageable))
        return "admins/member/getSearchMember"
    }

    // 한명의 회원 삭제
    @DeleteMapping("members/{memberId}")
    fun deleteOneMember(@PathVariable("memberId") memberId: Long): String {
        adminMemberService.deleteOneMember(memberId)
        return "redirect:/admins/members"
    }


    // 한명 이상의 회원 삭제
//    @DeleteMapping("members")
//    fun deleteMembers(@RequestBody deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO {
//        return adminMemberService.deleteMembers(deleteMembersDTO)
//    }
}
