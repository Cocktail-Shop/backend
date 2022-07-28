package com.lionTF.CShop.domain.shop.repository

import com.lionTF.CShop.domain.admin.models.Cocktail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.apache.ibatis.annotations.*


@Repository
interface CocktailRepository : JpaRepository<Cocktail, Long> {

}
