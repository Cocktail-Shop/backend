package com.lionTF.cshop.domain.member.handler

import com.lionTF.cshop.domain.member.models.MemberRole
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.RedirectStrategy
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAccessDeniedHandler(private val redirectStrategy: RedirectStrategy = DefaultRedirectStrategy()):AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {

        if(request?.isUserInRole("ROLE_${MemberRole.PREMEMBER.name}") == true){
            redirectStrategy.sendRedirect(request,response,"/pre-members/deny")//인증안된 사용자
        }else if(request?.isUserInRole("ROLE_${MemberRole.MEMBER.name}")==true) {
            redirectStrategy.sendRedirect(request, response, "/members/deny")//인증된 사용자가 admin 접근
        }else{
            redirectStrategy.sendRedirect(request,response,"/members/login")
        }
    }
}