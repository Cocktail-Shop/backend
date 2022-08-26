package com.lionTF.cshop.domain.member.controller.dto

import org.springframework.http.HttpStatus


data class MemberResponseDTO(
    val status: Int,
    val message: String,
    var href: String = ""
) {

    companion object {
        fun toFailedLoginResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "아이디 혹은 비밀번호를 확인해주세요. 또는 탈퇴한 회원입니다.", "/members/login")
        }

        fun toFailedSocialLoginResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "이메일 약관을 동의하지 않았거나 가입 기록이 있는 이메일입니다.(탈퇴 회원 포함)", "/members/login")
        }

        fun toSuccessSignUpResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.CREATED.value(), "아이디 생성 성공입니다.", "/members/login")
        }

        fun toFailedSignUpResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "가입 기록이 있는 아이디 또는 이메일 입니다. (탈퇴 회원 포함)", "/members/login")
        }

        fun toDeletedIdInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "탈퇴한 회원입니다.")
        }

        fun toFailedIdInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "존재하지 않는 회원입니다.")
        }

        fun toSocialIdInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "소셜 로그인 회원입니다.")
        }

        fun toSuccessPasswordInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.OK.value(), "임시비밀번호를 발송했습니다.", "/members/login")
        }

        fun toDeletedPasswordInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "탈퇴한 회원입니다.", "/members/login")
        }

        fun toFailedPasswordInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "존재하지 않는 회원입니다.", "/members/login")
        }

        fun toSocialPasswordInquiryResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "소셜 로그인 회원입니다.", "/members/login")
        }

        fun toSuccessMyPageUpdateResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.CREATED.value(), "마이페이지 정보가 성공적으로 수정되었습니다.", "/members")
        }

        fun toFailedMyPageUpdateResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.BAD_REQUEST.value(), "이미 사용중인 아이디 입니다.", "/members")
        }

        fun toSuccessPasswordUpdateResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.CREATED.value(), "비밀번호가 성공적으로 수정되었습니다.", "/members")
        }

        fun toFailedPasswordUpdateResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "비밀번호 변경이 불가능합니다. 기존 비밀번호와 새 비밀번호을 확인해주세요.", "/members")
        }

        fun toDeleteMemberResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.NO_CONTENT.value(), "회원 탈퇴 요청 완료", "/members/logout")
        }

        fun toMemberAccessDeniedResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.FORBIDDEN.value(), "접근 권한이 없습니다.", "/members")
        }

        fun toPreMemberAccessDeniedResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.FORBIDDEN.value(), "추가정보를 입력해주세요", "/pre-members")
        }

        fun toSuccessSetPreMemberInfoResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.OK.value(), "추가정보 입력 완료했습니다. 다시 로그인해주세요!", "/members/logout")
        }

        fun toSessionExpireResponseDTO(): MemberResponseDTO {
            return MemberResponseDTO(HttpStatus.UNAUTHORIZED.value(), "세션이 만료되었습니다. 다시 로그인 해주세요", "/members/login")
        }
    }
}
