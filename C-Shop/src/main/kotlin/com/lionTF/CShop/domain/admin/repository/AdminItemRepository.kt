package com.lionTF.CShop.domain.admin.repository

import com.lionTF.CShop.domain.admin.models.Item
import org.springframework.data.jpa.repository.JpaRepository

interface AdminItemRepository: JpaRepository<Item, Long> {
}