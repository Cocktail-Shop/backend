package com.lionTF.CShop.domain.admin.controller.dto

import java.time.LocalDateTime

data class FindCocktails(
    var cocktailName: String,
    var cocktailDescription: String,
    var createdDate: LocalDateTime
)
