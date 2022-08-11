package com.lionTF.CShop.domain.admin.controller.dto

import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.member.controller.dto.RequestSignUpDTO
import org.springframework.http.HttpStatus

// 상품 등록을 위한 정보가 실려오는 JSON 형태를 DB에 저장하기 위한 dto
data class RequestCreateItemDTO(
    var itemName: String="",
    var category: Category= Category.ALCOHOL,
    var price: Int = 0,
    var amount: Int = 0,
    var degree: Int = 10,
    var itemDescription: String = "",

) {
    companion object {
        fun toFormDTO(): RequestCreateItemDTO {
            return RequestCreateItemDTO()
        }
    }
}

// 상태코드와 message를 반환하기 위한 DTO
data class CreateItemResultDTO(
    var status: Int,
    var message: String,
)

// 등록 성공시 reposneBody에 저장되는 함수
fun setCreateSuccessItemResultDTO(): CreateItemResultDTO {
    return CreateItemResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "상품이 등록되었습니다."
    )
}

// 등록 실패시 reposneBody에 저장되는 함수
fun setCreateFailItemResultDTO(): CreateItemResultDTO {
    return CreateItemResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "이미 존재하는 상품입니다."
    )
}

// 수정 성공시 reposneBody에 저장되는 함수
fun setUpdateSuccessItemResultDTO(): CreateItemResultDTO {
    return CreateItemResultDTO(
        status = HttpStatus.CREATED.value(),
        message = "상품이 수정되었습니다."
    )
}

// 수정 실패시 reposneBody에 저장되는 함수
fun setUpdateFailItemResultDTO(): CreateItemResultDTO {
    return CreateItemResultDTO(
        status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
        message = "존재하지 않는 상품입니다."
    )
}