package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.models.Cocktail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.apache.ibatis.annotations.*
import org.springframework.data.querydsl.QuerydslPredicateExecutor

@Mapper
@Repository
interface ItemRepository : JpaRepository<Item, Long>,QuerydslPredicateExecutor<Item>{

}
