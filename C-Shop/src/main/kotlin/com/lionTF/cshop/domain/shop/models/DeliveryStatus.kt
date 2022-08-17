package com.lionTF.cshop.domain.shop.models

enum class DeliveryStatus(val description: String) {
    READY("배송 준비 중"), COMPLETE("배송 완료"), IN_DELIVERY("베송 중")
}
