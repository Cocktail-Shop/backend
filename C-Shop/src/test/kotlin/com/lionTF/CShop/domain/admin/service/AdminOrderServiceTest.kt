package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.repository.AdminOrderItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderItemDTO
import com.lionTF.CShop.domain.shop.models.OrderItem
import com.lionTF.CShop.domain.shop.models.OrderStatus
import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.domain.shop.repository.OrderRepository
import com.lionTF.CShop.domain.shop.service.shopinterface.OrderService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import javax.transaction.Transactional

@SpringBootTest
@Transactional
internal class AdminOrderServiceTest {

    @Autowired
    private lateinit var adminOrderService: AdminOrderService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var adminItemRepository: AdminItemRepository

    @Autowired
    private lateinit var memberAuthRepository: MemberAuthRepository

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var adminOrderItemRepository: AdminOrderItemRepository

    private var order: Orders? = null
    private var orderItem: OrderItem? = null
    private var item1: Item? = null
    private var item2: Item? = null
    private var item3: Item? = null
    private var memberTest1: Member? = null
    private var memberTest2: Member? = null
    private var memberTest3: Member? = null

    @BeforeEach
    fun init() {
        val orderDTO = Orders(
            orderAddress = "test",
            orderStatus = OrderStatus.COMPLETE
        )
        order = orderRepository.save(orderDTO)

        val orderItemDTO = OrderItem(
            amount = 5,
            orders = order
        )

        orderItem = adminOrderItemRepository.save(orderItemDTO)


        val itemDTO1 = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item1 = adminItemRepository.save(Item.requestCreateItemDTOtoItem(itemDTO1))

        val itemDTO2 = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item2 = adminItemRepository.save(Item.requestCreateItemDTOtoItem(itemDTO2))

        val itemDTO3 = RequestCreateItemDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item3 = adminItemRepository.save(Item.requestCreateItemDTOtoItem(itemDTO3))


        val member1 = Member(
            id = "test1",
            password= passwordEncoder.encode("test1"),
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동 XX빌딩 103호"
        )
        member1.role= MemberRole.MEMBER
        memberTest1 = memberAuthRepository.save(member1)

        val member2 = Member(
            id = "test2",
            password= passwordEncoder.encode("test1"),
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동 XX빌딩 103호"
        )
        member2.role= MemberRole.MEMBER
        memberTest2 = memberAuthRepository.save(member2)

        val member3 = Member(
            id = "lee",
            password= passwordEncoder.encode("test1"),
            phoneNumber = "01012341234",
            memberName = "사용자",
            address = "서울시 동작구 상도동 XX빌딩 103호"
        )
        member3.role= MemberRole.MEMBER
        memberTest3 = memberAuthRepository.save(member3)

        val requestOrderItemDTO1 = RequestOrderItemDTO(
            itemId = item1!!.itemId,
            amount = item1!!.amount,
            price = item1!!.price
        )
        var orderItems: MutableList<RequestOrderItemDTO> = mutableListOf()
        orderItems.add(requestOrderItemDTO1)

        val requestOrderDTO1 = RequestOrderDTO(
            memberId = memberTest1!!.memberId,
            orderItems = orderItems,
            Address = "test1",
            AddressDetail = "test1"
        )
        orderService.requestOrder(requestOrderDTO1)


        val requestOrderItemDTO2 = RequestOrderItemDTO(
            itemId = item2!!.itemId,
            amount = item2!!.amount,
            price = item2!!.price
        )
        var orderItems2: MutableList<RequestOrderItemDTO> = mutableListOf()
        orderItems2.add(requestOrderItemDTO2)

        val requestOrderDTO2 = RequestOrderDTO(
            memberId = memberTest2!!.memberId,
            orderItems = orderItems2,
            Address = "test2",
            AddressDetail = "test2"
        )
        orderService.requestOrder(requestOrderDTO2)


        val requestOrderItemDTO3 = RequestOrderItemDTO(
            itemId = item3!!.itemId,
            amount = item3!!.amount,
            price = item3!!.price
        )
        var orderItems3: MutableList<RequestOrderItemDTO> = mutableListOf()
        orderItems3.add(requestOrderItemDTO3)

        val requestOrderDTO3 = RequestOrderDTO(
            memberId = memberTest3!!.memberId,
            orderItems = orderItems3,
            Address = "test3",
            AddressDetail = "test3"
        )
        orderService.requestOrder(requestOrderDTO3)
    }


    @Test
    @DisplayName("하나의 주문 취소 test")
    fun cancelOneOrderTest() {
        //given

        //when
        val cancelOneOrder = adminOrderService.cancelOneOrder(order!!.orderId)

        //then
        assertThat(cancelOneOrder.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCancelOrderResponseDTO().httpStatus)
        assertThat(cancelOneOrder.message).isEqualTo(AdminResponseDTO.toSuccessCancelOrderResponseDTO().message)
        assertThat(order!!.orderStatus).isEqualTo(OrderStatus.CANCEL)
        println("cancelOneOrder = ${cancelOneOrder.message}")
    }

    @Test
    @DisplayName("하나의 주문 취소 예외 중 이미 취소된 주문을 취소할 case test")
    fun duplicatedCancelOneOrderExceptionTest() {
        //given
        adminOrderService.cancelOneOrder(order!!.orderId)

        //when
        val duplicatedCancelOneOrder = adminOrderService.cancelOneOrder(order!!.orderId)

        //then
        assertThat(duplicatedCancelOneOrder.httpStatus).isEqualTo(AdminResponseDTO.toFailCancelOrderByDuplicatedResponseDTO().httpStatus)
        assertThat(duplicatedCancelOneOrder.message).isEqualTo(AdminResponseDTO.toFailCancelOrderByDuplicatedResponseDTO().message)
        println("duplicatedCancelOneOrder = ${duplicatedCancelOneOrder.message}")
    }

    @Test
    @DisplayName("하나의 주문 취소 예외 test")
    fun deleteOneOrderExceptionTest() {
        //given
        val orderId: Long = 98L

        //when
        val cancelOneOrder = adminOrderService.cancelOneOrder(orderId)

        //then
        assertThat(cancelOneOrder.httpStatus).isEqualTo(AdminResponseDTO.toFailCancelOrderResponseDTO().httpStatus)
        assertThat(cancelOneOrder.message).isEqualTo(AdminResponseDTO.toFailCancelOrderResponseDTO().message)
        println("cancelOneOrder = ${cancelOneOrder.message}")
    }

    private fun generatePageable(page: Int, pageSize: Int): PageRequest = PageRequest.of(page, pageSize)

    @Test
    @DisplayName("주문 전체 조회 test")
    fun getAllOrdersTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize)

        val orderCount = orderRepository.count()

        //when
        val allOrders = adminOrderService.getAllOrders(pageable)

        //then
        assertThat(allOrders.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allOrders.message).isEqualTo("주문 조회를 성공했습니다.")
        assertThat(allOrders.keyword).isEqualTo("")
        assertThat(allOrders.result!!.totalElements).isEqualTo(orderCount - 1)
        assertThat(allOrders.result!!.totalPages).isEqualTo(3)

    }

    @Test
    @DisplayName("회원 ID로 주문 조회 test")
    fun getOrdersByMemberIdTest() {
        //given
        val page = 0
        val pageSize = 2
        val pageable = generatePageable(page = page, pageSize = pageSize)
        val keyword: String = "te"

        //when
        val ordersByMemberId = adminOrderService.getOrdersByMemberId(keyword, pageable)

        //then
        assertThat(ordersByMemberId.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(ordersByMemberId.message).isEqualTo("주문 조회를 성공했습니다.")
        assertThat(ordersByMemberId.keyword).isEqualTo(keyword)
        assertThat(ordersByMemberId.result!!.totalElements).isEqualTo(4)
        assertThat(ordersByMemberId.result!!.totalPages).isEqualTo(2)
    }


//    @Test
//    @DisplayName("주문 취소 test")
//    fun deleteOrdersTest() {
//        //given
//        var orderIds: MutableList<Long> = mutableListOf()
//
//        order?.let { orderIds.add(it.orderId) }
//
//        var deleteOrdersDTO = DeleteOrdersDTO(
//            orderIds
//        )
//
//        //when
//        val deleteOrders = adminOrderService.deleteOrders(deleteOrdersDTO)
//
//        //then
//        assertThat(deleteOrders.status).isEqualTo(setDeleteSuccessOrdersResultDTO().status)
//        assertThat(deleteOrders.message).isEqualTo(setDeleteSuccessOrdersResultDTO().message)
//        assertThat(order?.orderStatus).isEqualTo(OrderStatus.CANCEL)
//    }
//
//
//    @Test
//    @DisplayName("없는 주문을 취소할 예외 test")
//    fun deleteOrdersExceptionTest() {
//        //given
//        var orderIds: MutableList<Long> = mutableListOf()
//
//        orderIds.add(10L)
//
//        var deleteOrdersDTO = DeleteOrdersDTO(
//            orderIds
//        )
//
//        //when
//        val deleteOrders = adminOrderService.deleteOrders(deleteOrdersDTO)
//
//        //then
//        assertThat(deleteOrders.status).isEqualTo(setDeleteFailOrdersResultDTO().status)
//        assertThat(deleteOrders.message).isEqualTo(setDeleteFailOrdersResultDTO().message)
//        assertThat(order?.orderStatus).isEqualTo(OrderStatus.COMPLETE)
//    }
}