package com.lionTF.CShop.domain.member.service.memberinterface

import com.lionTF.CShop.domain.member.controller.dto.*

interface MemberService {
    //회원가입
    fun registerMember(requestSignUpDTO: RequestSignUpDTO): ResponseDTO

    //아이디찾기
    fun idInquiry(idInquiryDTO: RequestIdInquiryDTO):Any?

    //비밀번호 찾기
    fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseDTO

}