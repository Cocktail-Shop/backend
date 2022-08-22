package com.lionTF.cshop.domain.member.handler

import com.lionTF.cshop.domain.member.models.MemberRole
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler(
    private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        when {
            request?.isUserInRole("ROLE_${MemberRole.PREMEMBER.name}") == true -> redirectStrategy.sendRedirect(
                request,
                response,
                "/pre-members/deny"
            )
            request?.isUserInRole("ROLE_${MemberRole.MEMBER.name}") == true -> redirectStrategy.sendRedirect(
                request,
                response,
                "/members/deny"
            )
            else -> redirectStrategy.sendRedirect(request, response, "/members/login")
        }
    }
}
