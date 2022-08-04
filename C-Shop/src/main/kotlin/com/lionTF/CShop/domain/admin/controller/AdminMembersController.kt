package com.lionTF.CShop.domain.admin.controller

import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersDTO
import com.lionTF.CShop.domain.admin.controller.dto.DeleteMembersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminMemberService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminMembersController(
    private val adminMemberService: AdminMemberService,
) {

    @DeleteMapping("/admins/members")
    fun deleteMembers(@RequestBody deleteMembersDTO: DeleteMembersDTO): DeleteMembersResultDTO {
        return adminMemberService.deleteMembers(deleteMembersDTO)
    }
}