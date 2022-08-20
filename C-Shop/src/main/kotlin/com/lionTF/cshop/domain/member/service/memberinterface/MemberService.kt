package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.*

interface MemberService {
    fun registerMember(signUpRequestDTO: SignUpRequestDTO): MemberResponseDTO

    fun requestIdInquiry(idInquiryDTO: IdInquiryRequestDTO):Any?

    fun requestPasswordInquiry(passwordInquiryDTO: PasswordInquiryRequestDTO):MemberResponseDTO

    fun setPreMemberInfo(memberId:Long, preMemberInfoRequestDTO: PreMemberInfoRequestDTO):MemberResponseDTO
}

