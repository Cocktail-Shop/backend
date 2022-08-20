package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.RequestAuthNumberDTO
import com.lionTF.cshop.domain.member.controller.dto.RequestVerifyAuthNumberDTO

interface MailAuthService {
    fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO)

    fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO):Boolean
}
