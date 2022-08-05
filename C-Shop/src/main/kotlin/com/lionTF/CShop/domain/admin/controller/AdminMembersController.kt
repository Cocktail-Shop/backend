package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.FindMembersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class AdminMembersController(
    private val adminMemberService: AdminMemberService,
) {

    // 회원 삭제
    @DeleteMapping("/admins/members")
    fun deleteMembers(@RequestBody deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO {
        return adminMemberService.deleteMembers(deleteMembersDTO)
    }

    // 회원 ID로 회원 검색
    @GetMapping("/admins/search/members")
    fun findMembers(@RequestParam("keyword") keyword: String): FindMembersResultDTO {
        val findMembers = adminMemberService.findMembers(keyword)
        return FindMembersResultDTO(
            HttpStatus.OK.value(),
            "멤버 검색 성공",
            findMembers
        )
    }
}