package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.shop.models.Cart


//장바구니에 상품 추가시 저장할 때 사용하기 위한 dto
data class CartItemDTO(
    val item: Item,
    val cart: Cart,
    val amount: Int,
)
