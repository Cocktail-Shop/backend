package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.*

interface MyPageService {
    fun getMyPageInfo(memberId: Long): ResponseMyPageDTO

    fun updateMyPageInfo(memberId:Long,requestUpdateMyPageDTO: RequestUpdateMyPageDTO): ResponseDTO

    fun updatePassword(memberId:Long,requestUpdatePasswordDTO: RequestUpdatePasswordDTO):ResponseDTO

    fun deleteMember(authMemberDTO: AuthMemberDTO):ResponseDTO
}
