package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.admin.repository.custom.AdminOrderRepositoryCustom
import com.lionTF.CShop.domain.shop.models.Orders
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : JpaRepository<Orders, Long>, AdminOrderRepositoryCustom {
}