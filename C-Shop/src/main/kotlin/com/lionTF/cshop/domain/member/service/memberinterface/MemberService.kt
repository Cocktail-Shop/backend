package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.*

interface MemberService {
    fun registerMember(requestSignUpDTO: RequestSignUpDTO): ResponseDTO

    fun idInquiry(idInquiryDTO: RequestIdInquiryDTO):Any?

    fun passwordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseDTO

    fun setPreMemberInfo(memberId:Long,requestPreMemberInfoDTO: RequestPreMemberInfoDTO):ResponseDTO
}

