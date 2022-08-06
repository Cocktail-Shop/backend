package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
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
    // TODO URL 변경 예정입니다.
    @GetMapping("/admins/search/members")
    fun findMembers(@RequestParam("keyword") keyword: String): FindMembersResultDTO? {
        return adminMemberService.findMembers(keyword)
    }


    // 회원 전체 조회
    @GetMapping("/admins/members")
    fun getAllMembers(): FindMembersResultDTO? {
        return adminMemberService.getAllMembers()
    }
}