package com.lionTF.CShop.domain.member.service.memberinterface

import com.lionTF.CShop.domain.member.controller.dto.MailDTO
import com.lionTF.CShop.domain.member.controller.dto.RequestAuthNumberDTO
import com.lionTF.CShop.domain.member.controller.dto.RequestVerifyAuthNumberDTO

interface MailAuthService {
    //인증번호 전송
    fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO)

    //인증번호 검증
    fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO):Boolean

}