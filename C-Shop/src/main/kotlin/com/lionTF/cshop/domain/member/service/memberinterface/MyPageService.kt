package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.*

interface MyPageService {
    //마이페이지 조회
    fun getMyPageInfo(memberId: Long?): ResponseMyPageDTO

    //마이페이지 정보 수정
    fun updateMyPageInfo(memberId:Long?,requestUpdateMyPageDTO: RequestUpdateMyPageDTO): ResponseDTO

    //마이페이지 패스워드 수정
    fun updatePassword(memberId:Long?,requestUpdatePasswordDTO: RequestUpdatePasswordDTO):ResponseDTO

    //마이페이지 회원 탈퇴
    fun deleteMember(authMemberDTO: AuthMemberDTO?):ResponseDTO
}