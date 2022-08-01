package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.repository.AdminCocktailRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminCocktailService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AdminCocktailServiceImpl(
    private val adminCocktailRepository: AdminCocktailRepository

): AdminCocktailService{

}