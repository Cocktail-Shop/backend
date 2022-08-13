package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.domain.shop.repository.custom.OrderRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface OrderRepository : JpaRepository<Orders, Long>, OrderRepositoryCustom{
}