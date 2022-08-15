package com.lionTF.CShop.domain.shop.service.shopinterface

import com.lionTF.CShop.domain.member.controller.dto.AddressDTO
import com.lionTF.CShop.domain.shop.controller.dto.OrderResponseDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderResultDTO
import org.springframework.data.domain.Pageable

interface OrderService {
    //상품 주문 메소드
    fun requestOrder(requestOrderDTO: RequestOrderDTO) : RequestOrderResultDTO

    //주소 가져오기
    fun getAddress(memberId: Long) : AddressDTO

    // 상품 삭제
    fun cancelOrder(orderId: Long): OrderResponseDTO

    // 주문 조회
    fun getShopOrders(pageable: Pageable): OrderResponseDTO
}