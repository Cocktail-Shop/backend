package com.lionTF.CShop.domain.member.controller.dto

data class MailDTO (var toAddress:String,
                   var title:String,
                   var message:String,
                   val fromAddress:String){

    companion object{
        fun toPasswordInquiryMailDTO(id:String,toAddress: String,tempPw:String):MailDTO{
            val title = "$id 님 임시 비밀번호 안내 이메일 입니다."
            val message = """
            안녕하세요. $id 님 임시 비밀번호 안내 메일입니다.
            회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주시길 바랍니다. 
            임시 비밀번호 : $tempPw
            """.trimIndent()
            val fromAddress = "cshop1234@naver.com"           //TODO("yml파일로 따로빼서 사용하게하기")

            return MailDTO(
                toAddress,
                title,
                message,
                fromAddress
            )
        }
    }
}