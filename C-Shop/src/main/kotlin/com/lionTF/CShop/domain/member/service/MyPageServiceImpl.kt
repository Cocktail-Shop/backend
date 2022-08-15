package com.lionTF.CShop.domain.member.service

import com.lionTF.CShop.domain.member.controller.dto.*
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.member.service.memberinterface.MyPageService
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.repository.CartRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit


@Service
class MyPageServiceImpl(val memberAuthRepository: MemberAuthRepository) :MyPageService{

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder


    //마이페이지 정보 조회
    override fun getMyPageInfo(memberId: Long?): ResponseMyPageDTO {
        val member = memberAuthRepository.findByMemberId(memberId).orElseThrow()
        return ResponseMyPageDTO.fromMember(member)
    }

    //마이페이지 정보 수정
    override fun updateMyPageInfo(memberId:Long?,requestUpdateMyPageDTO: RequestUpdateMyPageDTO):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(memberId).orElseThrow()

        val existInfo=memberAuthRepository.findById(requestUpdateMyPageDTO.id)
        val canUpdate=!(existInfo.isPresent && existInfo.get().memberId!=memberId)//이미 존재하는 아이디면서 본인 아이디가 아닌 케이스가 아닌 케이스


        return if(canUpdate) {
            existMember.updateMember(requestUpdateMyPageDTO)
            memberAuthRepository.save(existMember)
            ResponseDTO.toSuccessUpdateMyPageResponseDTO()
        }else{
            ResponseDTO.toFailedUpdateMyPageResponseDTO()
        }

    }

    override fun updatePassword(memberId:Long?, requestUpdatePasswordDTO: RequestUpdatePasswordDTO):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(memberId).orElseThrow()

        val pastPassword=requestUpdatePasswordDTO.pastPassword
        val newPassword=requestUpdatePasswordDTO.newPassword

        val isMatchExistPassword = passwordEncoder.matches(pastPassword,existMember.password)
        val isPastSameNewPassword= pastPassword==newPassword

        val canUpdate=isMatchExistPassword&&!isPastSameNewPassword

        return if(canUpdate){
            existMember.updateMemberPassword(pastPassword,passwordEncoder)
            memberAuthRepository.save(existMember)
            ResponseDTO.toSuccessUpdatePasswordDTO()
        }else{
            ResponseDTO.toFailedUpdatePasswordDTO()
        }
    }

    //회원탈퇴
    override fun deleteMember(authMemberDTO: AuthMemberDTO?):ResponseDTO{
        val existMember=memberAuthRepository.findByMemberId(authMemberDTO?.memberId).orElseThrow()
        existMember.deleteMember()
        memberAuthRepository.save(existMember)
        return ResponseDTO.toDeleteMemberResponseDTO()
    }




}