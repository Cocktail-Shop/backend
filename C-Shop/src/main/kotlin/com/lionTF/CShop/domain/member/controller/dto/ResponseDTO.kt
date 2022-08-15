package com.lionTF.CShop.domain.member.controller.dto

import org.springframework.http.HttpStatus


data class ResponseDTO(val status:Int,val message:String,var href:String="") {
    companion object{
        fun toFailedLoginResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.UNAUTHORIZED.value(),"아이디 혹은 비밀번호를 확인해주세요.","/members/login")
        }
        fun toSuccessSignUpResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.CREATED.value(),"아이디 생성 성공입니다.","/members/login")
        }
        fun toFailedSignUpResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.UNAUTHORIZED.value(),"이미 존재하는 아이디입니다.")
        }

        fun toFailedIdInquiryResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.UNAUTHORIZED.value(),"존재하지 않는 회원입니다.")
        }

        fun toSuccessPasswordInquiryResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.OK.value(),"임시비밀번호를 발송했습니다.","/members/login")
        }

        fun toFailedPasswordInquiryResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.UNAUTHORIZED.value(),"존재하지 않는 회원입니다.","/members/login")
        }

        fun toSuccessUpdateMyPageResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.CREATED.value(),"마이페이지 정보가 성공적으로 수정되었습니다.","/members")
        }

        fun toFailedUpdateMyPageResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.BAD_REQUEST.value(),"이미 사용중인 아이디 입니다.","/members")
        }

        fun toSuccessUpdatePasswordDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.CREATED.value(),"비밀번호가 성공적으로 수정되었습니다.","/members")
        }

        fun toFailedUpdatePasswordDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.UNAUTHORIZED.value(),"비밀번호 변경이 불가능합니다. 기존 비밀번호와 새 비밀번호을 확인해주세요.","/members")
        }

        fun toDeleteMemberResponseDTO():ResponseDTO{
            return ResponseDTO(HttpStatus.NO_CONTENT.value(),"회원 탈퇴 요청 완료","/members/logout")
        }
    }
}