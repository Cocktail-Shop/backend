package com.lionTF.cshop.domain.member.service.memberinterface

import com.lionTF.cshop.domain.member.controller.dto.AuthNumberRequestDTO
import com.lionTF.cshop.domain.member.controller.dto.AuthNumberVerifyRequestDTO

interface MailAuthService {

    fun sendAuthNumber(authNumberDTO: AuthNumberRequestDTO)

    fun verifyAuthNumber(authNumberDTO: AuthNumberVerifyRequestDTO): Boolean
}
