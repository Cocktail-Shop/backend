package com.lionTF.cshop.domain.shop.repository

import com.lionTF.cshop.domain.shop.models.WishList
import org.springframework.data.jpa.repository.JpaRepository

interface WishListRepository : JpaRepository<WishList, Long> {
}