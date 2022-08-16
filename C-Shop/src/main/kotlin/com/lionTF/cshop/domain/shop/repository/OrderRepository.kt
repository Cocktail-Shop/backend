package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.admin.repository.custom.AdminOrderRepositoryCustom
import com.lionTF.cshop.domain.shop.models.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : JpaRepository<Orders, Long>, AdminOrderRepositoryCustom {
}