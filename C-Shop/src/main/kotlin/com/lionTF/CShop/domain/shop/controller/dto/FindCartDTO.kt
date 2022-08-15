package com.lionTF.CShop.domain.shop.controller.dto

import com.lionTF.CShop.domain.admin.models.Item
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import java.time.LocalDateTime


data class ResponseSearchCartResultDTO(
    var httpStatus: Int,
    var message: String,
    var result: Page<FindCartDTO>? = null
){
    companion object{
        fun cartToResponseCartSearchPageDTO(findCartDTO: Page<FindCartDTO>): ResponseSearchCartResultDTO {
            return ResponseSearchCartResultDTO(
                HttpStatus.OK.value(),
                "장바구니 조회를 성공했습니다.",
                findCartDTO
            )
        }
    }

}

data class FindCartDTO(
    var cartId: Long,
    var itemId: Long,
    var itemName: String,
    var price: Int,
    var item: Item,
    var amount: Int,
    var itemImgUrl: String,
    var memberId: Long,
)