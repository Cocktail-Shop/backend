package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.DeleteOrdersDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteFailOrdersResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.setDeleteSuccessOrdersResultDTO
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.CShop.domain.shop.models.OrderStatus
import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.domain.shop.repository.OrderRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminOrderServiceTest {

    @Autowired
    private lateinit var adminOrderService: AdminOrderService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    private var order: Orders? = null

    @BeforeEach
    fun init() {
        var orderDTO = Orders(
            orderAddress = "test"
        )

        order = orderRepository.save(orderDTO)
    }


    @Test
    @DisplayName("주문 취소 test")
    fun deleteOrdersTest() {
        //given
        var orderIds: MutableList<Long> = mutableListOf()

        order?.let { orderIds.add(it.orderId) }

        var deleteOrdersDTO = DeleteOrdersDTO(
            orderIds
        )

        //when
        val deleteOrders = adminOrderService.deleteOrders(deleteOrdersDTO)

        //then
        assertThat(deleteOrders.status).isEqualTo(setDeleteSuccessOrdersResultDTO().status)
        assertThat(deleteOrders.message).isEqualTo(setDeleteSuccessOrdersResultDTO().message)
        assertThat(order?.orderStatus).isEqualTo(OrderStatus.CANCEL)
    }


    @Test
    @DisplayName("없는 주문을 취소할 예외 test")
    fun deleteOrdersExceptionTest() {
        //given
        var orderIds: MutableList<Long> = mutableListOf()

        orderIds.add(10L)

        var deleteOrdersDTO = DeleteOrdersDTO(
            orderIds
        )

        //when
        val deleteOrders = adminOrderService.deleteOrders(deleteOrdersDTO)

        //then
        assertThat(deleteOrders.status).isEqualTo(setDeleteFailOrdersResultDTO().status)
        assertThat(deleteOrders.message).isEqualTo(setDeleteFailOrdersResultDTO().message)
        assertThat(order?.orderStatus).isEqualTo(OrderStatus.COMPLETE)
    }

}