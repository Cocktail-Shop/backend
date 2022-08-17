package com.lionTF.cshop.domain.admin.repository

import com.lionTF.cshop.domain.admin.repository.custom.AdminOrderRepositoryCustom
import com.lionTF.cshop.domain.shop.models.Orders
import org.springframework.data.jpa.repository.JpaRepository

interface AdminOrderRepository: JpaRepository<Orders, Long>, AdminOrderRepositoryCustom {
}
