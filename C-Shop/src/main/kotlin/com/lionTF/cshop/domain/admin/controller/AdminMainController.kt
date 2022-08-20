package com.lionTF.cshop.domain.admin.controller

import com.lionTF.cshop.domain.admin.service.admininterface.AdminOrderService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admins")
class AdminMainController(
    private val adminOrderService: AdminOrderService
) {

    @GetMapping("main")
    fun getMainPage(
        pageable: Pageable,
        model: Model
    ): String {
        model.addAttribute("sales", adminOrderService.getAllSales(pageable))
        return "admins/main"
    }
}
