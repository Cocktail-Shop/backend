package com.lionTF.cshop.global.controller

import com.lionTF.cshop.domain.member.controller.dto.MemberResponseDTO
import com.lionTF.cshop.global.controller.dto.ErrorMessageDTO
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest


@Controller
class BasicErrorController : ErrorController {

    @GetMapping("/error")
    fun handleError(model: Model, request: HttpServletRequest): String {
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)

        if (status != null) {
            val statusCode = Integer.valueOf(status.toString())
            model.addAttribute("result", ErrorMessageDTO.toBasicErrorMessageDTO(statusCode))
        } else {
            model.addAttribute("result", MemberResponseDTO.toMemberAccessDeniedResponseDTO())
        }
        return "global/BasicError"
    }
}
