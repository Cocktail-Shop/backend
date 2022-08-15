package com.lionTF.CShop.domain.member.controller


import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.service.memberinterface.MailAuthService
import com.lionTF.CShop.domain.member.service.memberinterface.MemberService
import com.lionTF.CShop.domain.member.service.memberinterface.MyPageService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class MemberController(private val memberService:MemberService,
                       private val myPageService: MyPageService,
                       private val mailAuthService: MailAuthService) {

    //로그인 관련
    @GetMapping("/members/login")
    fun login():String{
        return "members/login"
    }

    @GetMapping("/members/login-fail")
    fun loginFail(model: Model):String{
        model.addAttribute("result",ResponseDTO.toFailedLoginResponseDTO())
        return "global/message"
    }

    //인증번호 관련
    @PostMapping("/members/auth-number")
    @ResponseBody//AJAX 사용시 필요
    fun sendAuthNumber(authNumberDTO: RequestAuthNumberDTO){
        mailAuthService.sendAuthNumber(authNumberDTO)
    }

    @PostMapping("/members/auth-number/verify")
    @ResponseBody//AJAX 사용시 필요
    fun verifyAuthNumber(authNumberDTO: RequestVerifyAuthNumberDTO):Boolean{
        return mailAuthService.verifyAuthNumber(authNumberDTO)
    }

    // 아이디 찾기
    @GetMapping("/members/id-inquiry")
    fun idInquiryPage( model: Model):String{
        model.addAttribute("requestIdInquiryDTO",RequestIdInquiryDTO.toFormDTO())
        return "members/forget-id"
    }

    @PostMapping("/members/id-inquiry")
    fun idInquiry(requestIdInquiryDTO: RequestIdInquiryDTO,model: Model):String{
        model.addAttribute("idInquiryResult",memberService.idInquiry(requestIdInquiryDTO))
        return "members/forget-id-result"
    }

    // 비밀번호 찾기
    @GetMapping("/members/password-inquiry")
    fun pwInquiryPage(model: Model):String{
        model.addAttribute("requestPasswordInquiryDTO",RequestPasswordInquiryDTO.toFormDTO())
        return "members/forget-password"
    }

    @PostMapping("/members/password-inquiry")
    fun passwordInquiry(requestPasswordInquiryDTO: RequestPasswordInquiryDTO,model: Model):String{
        model.addAttribute("result",memberService.passwordInquiry(requestPasswordInquiryDTO))
        return "global/message"
    }

    //회원가입
    @GetMapping("/members/signup")
    fun signUpPage(model: Model):String{
        model.addAttribute("requestSignUpDTO",RequestSignUpDTO.toFormDTO())
        return "members/signup"
    }

    @PostMapping("/members/signup")
    fun signUp(requestSignUpDTO: RequestSignUpDTO,model: Model):String{
        //ResponseEntity<ResponseDTO>{
        model.addAttribute("result",memberService.registerMember(requestSignUpDTO))
        return "global/message"
    }


    //마이페이지
    @GetMapping("/members")
    fun getMyPageInfo(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,model: Model): String {
        val responseDTO =myPageService.getMyPageInfo(authMemberDTO?.memberId)
        model.addAttribute("myPageInfo",responseDTO)
        model.addAttribute("requestUpdateMyPageDTO",RequestUpdateMyPageDTO.toFormDTO(responseDTO))
        return "members/my-page"
    }

    @PutMapping("/members")
    fun updateMyPageInfo(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?, requestUpdateMyPageDTO:RequestUpdateMyPageDTO,model: Model):String{
        model.addAttribute("result",myPageService.updateMyPageInfo(authMemberDTO?.memberId,requestUpdateMyPageDTO))
        return "global/message"
    }

    //마이페이지 비밀번호 수정
    @GetMapping("/members/password")
    fun getUpdatePasswordPage(model:Model):String{
        model.addAttribute("requestUpdatePasswordDTO",RequestUpdatePasswordDTO.toFormDTO())
        return "members/reassign-forget-password"
    }

    @PutMapping("/members/password")
    fun updatePassword(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,requestUpdatePasswordDTO: RequestUpdatePasswordDTO,model:Model):String{
        model.addAttribute("result",myPageService.updatePassword(authMemberDTO?.memberId,requestUpdatePasswordDTO))
        return "global/message"
    }


    //회원 탈퇴
    @DeleteMapping("/members")
    fun deleteMember(@AuthenticationPrincipal authMemberDTO: AuthMemberDTO?,model:Model):String{
        model.addAttribute("result",  myPageService.deleteMember(authMemberDTO))
        return "global/message"
    }

}