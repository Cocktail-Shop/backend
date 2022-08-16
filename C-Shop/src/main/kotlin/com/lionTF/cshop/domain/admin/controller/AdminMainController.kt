package com.lionTF.cshop.domain.admin.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admins")
class AdminMainController {

    @GetMapping("main")
    fun mainPage(model: Model): String {
        return "admins/main"
    }
}