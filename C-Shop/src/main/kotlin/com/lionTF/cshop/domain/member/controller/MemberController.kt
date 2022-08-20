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
class MemberController(
    private val memberService: MemberService,
    private val myPageService: MyPageService,
    private val mailAuthService: MailAuthService
) {
    @GetMapping("/getSessionId")
    fun getSessionId(session:HttpSession,model: Model):String{
        model.addAttribute("result",
            ResponseDTO(HttpStatus.OK.value(),session.id,"/members"))
        return "global/message"
    }

    @GetMapping("/members/login")
    fun login(): String {
        return "members/login"
    }

    @GetMapping("/members/login-fail")
    fun loginFail(model: Model): String {
        model.addAttribute("result", ResponseDTO.toFailedLoginResponseDTO())
        return "global/message"
    }

    @PostMapping("/members/auth-number")
    @ResponseBody
    fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO) {
        mailAuthService.sendAuthNumber(authNumberDTO)
    }

    @PostMapping("/members/auth-number/verify")
    @ResponseBody
    fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO): Boolean {
        println(authNumberDTO)
        return mailAuthService.verifyAuthNumber(authNumberDTO)
    }

    @GetMapping("/members/id-inquiry")
    fun idInquiryPage(model: Model): String {
        model.addAttribute("requestIdInquiryDTO", RequestIdInquiryDTO.toFormDTO())
        return "members/forget-id"
    }

    @PostMapping("/members/id-inquiry")
    fun idInquiry(requestIdInquiryDTO: RequestIdInquiryDTO, model: Model): String {
        model.addAttribute("idInquiryResult", memberService.idInquiry(requestIdInquiryDTO))
        return "members/forget-id-result"
    }

    @GetMapping("/members/password-inquiry")
    fun pwInquiryPage(model: Model): String {
        model.addAttribute("requestPasswordInquiryDTO", RequestPasswordInquiryDTO.toFormDTO())
        return "members/forget-password"
    }

    @PostMapping("/members/password-inquiry")
    fun passwordInquiry(requestPasswordInquiryDTO: RequestPasswordInquiryDTO, model: Model): String {
        model.addAttribute("result", memberService.passwordInquiry(requestPasswordInquiryDTO))
        return "global/message"
    }

    @GetMapping("/members/signup")
    fun signUpPage(model: Model): String {
        model.addAttribute("requestSignUpDTO", RequestSignUpDTO.toFormDTO())
        return "members/signup"
    }

    @PostMapping("/members/signup")
    fun signUp(requestSignUpDTO: RequestSignUpDTO, model: Model): String {
        //ResponseEntity<ResponseDTO>{
        model.addAttribute("result", memberService.registerMember(requestSignUpDTO))
        return "global/message"
    }

    @GetMapping("/members")
    fun getMyPageInfo(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO, model: Model): String {
        val responseDTO = myPageService.getMyPageInfo(authMemberDTO.memberId)
        model.addAttribute("myPageInfo", responseDTO)
        model.addAttribute("requestUpdateMyPageDTO", RequestUpdateMyPageDTO.toFormDTO(responseDTO))

        return when (authMemberDTO.fromSocial) {
            true -> "members/social-my-page"
            else -> "members/my-page"
        }
    }

    @PutMapping("/members")
    fun updateMyPageInfo(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        requestUpdateMyPageDTO: RequestUpdateMyPageDTO,
        model: Model
    ): String {
        model.addAttribute("result", myPageService.updateMyPageInfo(authMemberDTO.memberId, requestUpdateMyPageDTO))
        return "global/message"
    }

    @GetMapping("/members/password")
    fun getUpdatePasswordPage(model: Model): String {
        model.addAttribute("requestUpdatePasswordDTO", RequestUpdatePasswordDTO.toFormDTO())
        return "members/reassign-forget-password"
    }

    @PutMapping("/members/password")
    fun updatePassword(
        @AuthenticationPrincipal authMemberDTO: AuthMemberDTO,
        requestUpdatePasswordDTO: RequestUpdatePasswordDTO,
        model: Model
    ): String {
        model.addAttribute("result", myPageService.updatePassword(authMemberDTO.memberId, requestUpdatePasswordDTO))
        return "global/message"
    }

    @DeleteMapping("/members")
    fun deleteMember(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO, model: Model): String {
        model.addAttribute("result", myPageService.deleteMember(authMemberDTO))
        return "global/message"
    }

    @GetMapping("/pre-members/deny")
    fun getPreMemberAccessDeniedPage(model: Model):String{
        model.addAttribute("result",ResponseDTO.toPreMemberAccessDeniedResponseDTO())
        return "global/message"
    }

    @GetMapping("/members/deny")
    fun getMembersAccessDeniedPage(model: Model):String{
        model.addAttribute("result",ResponseDTO.toMemberAccessDeniedResponseDTO())
        return "global/message"
    }

    @GetMapping("/pre-members")
    fun getPreMemberPage(model: Model):String{
        model.addAttribute("requestPreMemberInfoDTO",RequestPreMemberInfoDTO.toFormDTO())
        return "members/pre-member-page"
    }

    @PostMapping("/pre-members")
    fun setPreMembersInfo(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO,requestPreMemberInfoDTO: RequestPreMemberInfoDTO,model:Model):String{
        model.addAttribute("result",memberService.setPreMemberInfo(authMemberDTO.memberId,requestPreMemberInfoDTO))
        return "global/message"
    }
}
