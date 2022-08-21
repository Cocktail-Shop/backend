package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.*

interface MyPageService {
    fun getMyPageInfo(memberId: Long): MyPageResponseDTO

    fun updateMyPageInfo(memberId:Long,requestUpdateMyPageDTO: RequestUpdateMyPageDTO): MemberResponseDTO

    fun updatePassword(memberId:Long, passwordUpdateRequestDTO: PasswordUpdateRequestDTO):MemberResponseDTO

    fun deleteMember(authMemberDTO: AuthMemberDTO):MemberResponseDTO
}
