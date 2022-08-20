package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.*

interface MemberService {
    fun registerMember(requestSignUpDTO: RequestSignUpDTO): ResponseDTO

    fun requestIdInquiry(idInquiryDTO: RequestIdInquiryDTO):Any?

    fun requestPasswordInquiry(passwordInquiryDTO: RequestPasswordInquiryDTO):ResponseDTO

    fun setPreMemberInfo(memberId:Long,requestPreMemberInfoDTO: RequestPreMemberInfoDTO):ResponseDTO
}

