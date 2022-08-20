package com.lionTF.cshop.domain.member.controller


import com.lionTF.cshop.domain.member.controller.dto.*
import com.lionTF.cshop.domain.member.service.memberinterface.MailAuthService
import com.lionTF.cshop.domain.member.service.memberinterface.MemberService
import com.lionTF.cshop.domain.member.service.memberinterface.MyPageService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpSession


@Controller
@RequestMapping("/members")
class MemberController(
    private val memberService: MemberService,
    private val myPageService: MyPageService,
    private val mailAuthService: MailAuthService
) {

    @GetMapping("/login")
    fun getLoginPage(): String {
        return "members/login"
    }

    @GetMapping("/login-fail")
    fun getLoginFailPage(model: Model): String {
        model.addAttribute("result", ResponseDTO.toFailedLoginResponseDTO())
        return "global/message"
    }

    @PostMapping("/auth-number")
    @ResponseBody
    fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO) {
        mailAuthService.sendAuthNumber(authNumberDTO)
    }

    @PostMapping("/auth-number/verify")
    @ResponseBody
    fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO): Boolean {
        println(authNumberDTO)
        return mailAuthService.verifyAuthNumber(authNumberDTO)
    }

    @GetMapping("/id-inquiry")
    fun getIdInquiryPage(model: Model): String {
        model.addAttribute("requestIdInquiryDTO", RequestIdInquiryDTO.toFormDTO())
        return "members/forget-id"
    }

    @PostMapping("/id-inquiry")
    fun requestIdInquiry(requestIdInquiryDTO: RequestIdInquiryDTO, model: Model): String {
        model.addAttribute("idInquiryResult", memberService.requestIdInquiry(requestIdInquiryDTO))
        return "members/forget-id-result"
    }

    @GetMapping("/password-inquiry")
    fun getPasswordInquiryPage(model: Model): String {
        model.addAttribute("requestPasswordInquiryDTO", RequestPasswordInquiryDTO.toFormDTO())
        return "members/forget-password"
    }

    @PostMapping("/password-inquiry")
    fun requestPasswordInquiry(requestPasswordInquiryDTO: RequestPasswordInquiryDTO, model: Model): String {
        model.addAttribute("result", memberService.requestPasswordInquiry(requestPasswordInquiryDTO))
        return "global/message"
    }

    @GetMapping("/signup")
    fun getSignUpPage(model: Model): String {
        model.addAttribute("requestSignUpDTO", RequestSignUpDTO.toFormDTO())
        return "members/signup"
    }

    @PostMapping("/signup")
    fun requestSignUp(requestSignUpDTO: RequestSignUpDTO, model: Model): String {
        model.addAttribute("result", memberService.registerMember(requestSignUpDTO))
        return "global/message"
    }

    @GetMapping
    fun getMyPageInfo(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO, model: Model): String {
        val responseDTO = myPageService.getMyPageInfo(authMemberDTO.memberId)
        model.addAttribute("myPageInfo", responseDTO)
        model.addAttribute("requestUpdateMyPageDTO", RequestUpdateMyPageDTO.toFormDTO(responseDTO))

        return when (authMemberDTO.fromSocial) {
            true -> "members/social-my-page"
            else -> "members/my-page"
        }
    }

    @PutMapping
    fun updateMyPageInfo(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        requestUpdateMyPageDTO: RequestUpdateMyPageDTO,
        model: Model
    ): String {
        model.addAttribute("result", myPageService.updateMyPageInfo(authMemberDTO.memberId, requestUpdateMyPageDTO))
        return "global/message"
    }

    @GetMapping("/password")
    fun getPasswordUpdatePage(model: Model): String {
        model.addAttribute("requestUpdatePasswordDTO", RequestUpdatePasswordDTO.toFormDTO())
        return "members/reassign-forget-password"
    }

    @PutMapping("/password")
    fun requestUpdatePassword(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        requestUpdatePasswordDTO: RequestUpdatePasswordDTO,
        model: Model
    ): String {
        model.addAttribute("result", myPageService.updatePassword(authMemberDTO.memberId, requestUpdatePasswordDTO))
        return "global/message"
    }

    @DeleteMapping
    fun deleteMember(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO, model: Model): String {
        model.addAttribute("result", myPageService.deleteMember(authMemberDTO))
        return "global/message"
    }

    @GetMapping("/deny")
    fun getMembersAccessDeniedPage(model: Model): String {
        model.addAttribute("result", ResponseDTO.toMemberAccessDeniedResponseDTO())
        return "global/message"
    }
}
