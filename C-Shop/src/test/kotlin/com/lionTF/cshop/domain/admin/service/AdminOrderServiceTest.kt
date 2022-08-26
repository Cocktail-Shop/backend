package com.lionTF.cshop.domain.admin.service

import com.lionTF.cshop.domain.admin.controller.dto.*
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.admin.repository.AdminOrderItemRepository
import com.lionTF.cshop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.models.MemberRole
import com.lionTF.cshop.domain.member.repository.MemberAuthRepository
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.cshop.domain.shop.controller.dto.RequestOrderItemDTO
import com.lionTF.cshop.domain.shop.models.DeliveryStatus
import com.lionTF.cshop.domain.shop.models.OrderItem
import com.lionTF.cshop.domain.shop.models.OrderStatus
import com.lionTF.cshop.domain.shop.models.Orders
import com.lionTF.cshop.domain.shop.repository.OrderRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.OrderService
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
    private val adminOrderService: AdminOrderService? = null

    @Autowired
    private val orderRepository: OrderRepository? = null

    @Autowired
    private val adminItemRepository: AdminItemRepository? = null

    @Autowired
    private val memberAuthRepository: MemberAuthRepository? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val orderService: OrderService? = null

    @Autowired
    private val adminOrderItemRepository: AdminOrderItemRepository? = null

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
        order = orderRepository?.save(orderDTO)

        val orderItemDTO = OrderItem(
            amount = 5,
            orders = order
        )

        orderItem = adminOrderItemRepository?.save(orderItemDTO)


        val itemDTO1 = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item1 = adminItemRepository?.save(Item.fromItemCreateRequestDTO(itemDTO1,"test"))

        val itemDTO2 = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item2 = adminItemRepository?.save(Item.fromItemCreateRequestDTO(itemDTO2, "test"))

        val itemDTO3 = ItemCreateRequestDTO(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item3 = adminItemRepository?.save(Item.fromItemCreateRequestDTO(itemDTO3, "test"))


        val member1 = passwordEncoder?.encode("test1")?.let {
            Member(
                id = "test1",
                password= it,
                phoneNumber = "01012341234",
                memberName = "사용자",
                address = "서울시 동작구 상도동 XX빌딩 103호",
                role = MemberRole.MEMBER
            )
        }
        member1?.role= MemberRole.MEMBER
        memberTest1 = memberAuthRepository?.save(member1!!)

        val member2 = passwordEncoder?.encode("test1")?.let {
            Member(
                id = "test2",
                password= it,
                phoneNumber = "01012341234",
                memberName = "사용자",
                address = "서울시 동작구 상도동 XX빌딩 103호",
                role = MemberRole.MEMBER
            )
        }
        member2?.role= MemberRole.MEMBER
        memberTest2 = memberAuthRepository?.save(member2!!)

        val member3 = passwordEncoder?.encode("test1")?.let {
            Member(
                id = "lee",
                password= it,
                phoneNumber = "01012341234",
                memberName = "사용자",
                address = "서울시 동작구 상도동 XX빌딩 103호",
                role = MemberRole.MEMBER
            )
        }
        member3?.role= MemberRole.MEMBER
        memberTest3 = memberAuthRepository?.save(member3!!)

        val requestOrderItemDTO1 = RequestOrderItemDTO(
            itemId = item1!!.itemId,
            amount = item1!!.amount,
            price = item1!!.price
        )
        val orderItems: MutableList<RequestOrderItemDTO> = mutableListOf()
        orderItems.add(requestOrderItemDTO1)

        val requestOrderDTO1 = RequestOrderDTO(
            memberId = memberTest1!!.memberId,
            orderItems = orderItems,
            address = "test1",
            addressDetail = "test1"
        )
        orderService?.requestOrder(requestOrderDTO1)


        val requestOrderItemDTO2 = RequestOrderItemDTO(
            itemId = item2!!.itemId,
            amount = item2!!.amount,
            price = item2!!.price
        )
        val orderItems2: MutableList<RequestOrderItemDTO> = mutableListOf()
        orderItems2.add(requestOrderItemDTO2)

        val requestOrderDTO2 = RequestOrderDTO(
            memberId = memberTest2!!.memberId,
            orderItems = orderItems2,
            address = "test2",
            addressDetail = "test2"
        )
        orderService?.requestOrder(requestOrderDTO2)


        val requestOrderItemDTO3 = RequestOrderItemDTO(
            itemId = item3!!.itemId,
            amount = item3!!.amount,
            price = item3!!.price
        )
        val orderItems3: MutableList<RequestOrderItemDTO> = mutableListOf()
        orderItems3.add(requestOrderItemDTO3)

        val requestOrderDTO3 = RequestOrderDTO(
            memberId = memberTest3!!.memberId,
            orderItems = orderItems3,
            address = "test3",
            addressDetail = "test3"
        )
        orderService?.requestOrder(requestOrderDTO3)
    }


    @Test
    @DisplayName("하나의 주문 취소 test")
    fun cancelOneOrderTest() {
        //given

        //when
        val cancelOneOrder = order?.let { adminOrderService?.cancelOneOrder(it.orderId) }

        //then
        assertThat(cancelOneOrder?.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCancelOrderResponseDTO().httpStatus)
        assertThat(cancelOneOrder?.message).isEqualTo(AdminResponseDTO.toSuccessCancelOrderResponseDTO().message)
        assertThat(order?.orderStatus).isEqualTo(OrderStatus.CANCEL)
        println("cancelOneOrder = ${cancelOneOrder?.message}")
    }

    @Test
    @DisplayName("하나의 주문 취소 예외 중 이미 취소된 주문을 취소할 case test")
    fun duplicatedCancelOneOrderExceptionTest() {
        //given
        order?.let { adminOrderService?.cancelOneOrder(it.orderId) }

        //when
        val duplicatedCancelOneOrder = order?.let { adminOrderService?.cancelOneOrder(it.orderId) }

        //then
        assertThat(duplicatedCancelOneOrder?.httpStatus).isEqualTo(AdminResponseDTO.toFailCancelOrderByDuplicatedResponseDTO().httpStatus)
        assertThat(duplicatedCancelOneOrder?.message).isEqualTo(AdminResponseDTO.toFailCancelOrderByDuplicatedResponseDTO().message)
        println("duplicatedCancelOneOrder = ${duplicatedCancelOneOrder?.message}")
    }

    @Test
    @DisplayName("하나의 주문 취소 예외 test")
    fun cancelOneOrderExceptionTest() {
        //given
        val orderId = 98L

        //when
        val cancelOneOrder = adminOrderService?.cancelOneOrder(orderId)

        //then
        assertThat(cancelOneOrder?.httpStatus).isEqualTo(AdminResponseDTO.toFailCancelOrderResponseDTO().httpStatus)
        assertThat(cancelOneOrder?.message).isEqualTo(AdminResponseDTO.toFailCancelOrderResponseDTO().message)
        println("cancelOneOrder = ${cancelOneOrder?.message}")
    }


    @Test
    @DisplayName("주문 취소 시 이미 배달이 완료된 주문은 취소가 불가능한 test")
    fun cancelOneOrderExceptionByDeliveryStatusTest() {
        //given
        val orderId: Long? = order?.orderId
        order?.deliveryStatus = DeliveryStatus.COMPLETE

        //when
        val cancelOneOrder = orderId?.let { adminOrderService?.cancelOneOrder(it) }


        //then
        assertThat(cancelOneOrder?.httpStatus).isEqualTo(AdminResponseDTO.toFailCancelOrderByCompleteDeliveryResponseDTO().httpStatus)
        assertThat(cancelOneOrder?.message).isEqualTo(AdminResponseDTO.toFailCancelOrderByCompleteDeliveryResponseDTO().message)
        println("cancelOneOrder = ${cancelOneOrder?.message}")
    }

    private fun generatePageable(page: Int = 0, pageSize: Int = 2): PageRequest = PageRequest.of(page, pageSize)

    @Test
    @DisplayName("주문 전체 조회 test")
    fun getAllOrdersTest() {
        //given
        val pageable = generatePageable()

        val orderCount = orderRepository?.count()

        //when
        val allOrders = adminOrderService?.getAllOrders(pageable)

        //then
        assertThat(allOrders?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(allOrders?.message).isEqualTo("주문 조회를 성공했습니다.")
        assertThat(allOrders?.keyword).isEqualTo("")
        assertThat(allOrders?.result?.content?.get(0)?.deliveryStatus).isEqualTo(DeliveryStatus.IN_DELIVERY)
        assertThat(allOrders?.result?.totalElements).isEqualTo(orderCount?.minus(1))
        assertThat(allOrders?.result?.totalPages).isEqualTo(4)

    }

    @Test
    @DisplayName("회원 ID로 주문 조회 test")
    fun getOrdersByMemberIdTest() {
        //given
        val pageable = generatePageable()
        val keyword = "te"

        //when
        val ordersByMemberId = adminOrderService?.getOrdersByMemberId(keyword, pageable)

        //then
        assertThat(ordersByMemberId?.httpStatus).isEqualTo(HttpStatus.OK.value())
        assertThat(ordersByMemberId?.message).isEqualTo("주문 조회를 성공했습니다.")
        assertThat(ordersByMemberId?.keyword).isEqualTo(keyword)
        assertThat(ordersByMemberId?.result?.totalElements).isEqualTo(7)
        assertThat(ordersByMemberId?.result?.totalPages).isEqualTo(4)
    }

    @Test
    @DisplayName("배달 상태 : 준비 -> 배송 중으로 변경 test")
    fun changeDeliveryReadyTest() {
        //given
        val orderId = order?.orderId

        //when
        orderId?.let { adminOrderService?.updateDeliveryInDelivery(it) }

        //then
        assertThat(order?.deliveryStatus).isEqualTo(DeliveryStatus.IN_DELIVERY)
    }

    @Test
    @DisplayName("배달 상태 : 준비 -> 배송 중으로 변경 중 없는 주문 번호로 할때 예외 test")
    fun changeDeliveryReadyExceptionTest() {
        //given
        val orderId = 123123L

        //when
        val deliveryStatus = adminOrderService?.updateDeliveryInDelivery(orderId)

        //then
        assertThat(deliveryStatus?.httpStatus).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatus().httpStatus)
        assertThat(deliveryStatus?.message).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatus().message)
        assertThat(order?.deliveryStatus).isEqualTo(DeliveryStatus.READY)
        println("deliveryStatus = ${deliveryStatus?.message}")
    }

    @Test
    @DisplayName("배달 상태 : 준비 -> 배송 중으로 변경 중 이미 취소된 주문에 대한 상태를 변경 할때 예외 test")
    fun changeDeliveryReadyExceptionByCancelOrderTest() {
        //given
        val orderId = order?.orderId
        orderId?.let { adminOrderService?.cancelOneOrder(it) }

        //when
        val deliveryStatus = orderId?.let { adminOrderService?.updateDeliveryInDelivery(it) }

        //then
        assertThat(deliveryStatus?.httpStatus).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatusByCancelOrder().httpStatus)
        assertThat(deliveryStatus?.message).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatusByCancelOrder().message)
        assertThat(order?.deliveryStatus).isEqualTo(DeliveryStatus.REFUND)
        println("deliveryStatus = ${deliveryStatus?.message}")
    }

    @Test
    @DisplayName("배달 상태 : 배송 중 -> 배송 완료로 변경 test")
    fun changeDeliveryComplete() {
        //given
        val orderId = order?.orderId

        //when
        orderId?.let { adminOrderService?.updateDeliveryComplete(it) }

        //then
        assertThat(order?.deliveryStatus).isEqualTo(DeliveryStatus.COMPLETE)
    }

    @Test
    @DisplayName("배달 상태 : 배송 중 -> 배송 완료로 변경 중 없는 주문 번호로 할때 예외 test")
    fun changeDeliveryCompleteExceptionTest() {
        //given
        val orderId = 123123L

        //when
        val deliveryStatus = adminOrderService?.updateDeliveryComplete(orderId)

        //then
        assertThat(deliveryStatus?.httpStatus).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatus().httpStatus)
        assertThat(deliveryStatus?.message).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatus().message)
        assertThat(order?.deliveryStatus).isEqualTo(DeliveryStatus.READY)
        println("deliveryStatus = ${deliveryStatus?.message}")
    }

    @Test
    @DisplayName("배달 상태 : 배송 중 -> 배송 완료로 변경 중 이미 취소된 주문에 대한 상태를 변경 할때 예외 test")
    fun changeDeliveryCompleteExceptionByCancelOrderTest() {
        //given
        val orderId = order?.orderId
        orderId?.let { adminOrderService?.cancelOneOrder(it) }

        //when
        val deliveryStatus = orderId?.let { adminOrderService?.updateDeliveryComplete(it) }

        //then
        assertThat(deliveryStatus?.httpStatus).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatusByCancelOrder().httpStatus)
        assertThat(deliveryStatus?.message).isEqualTo(AdminResponseDTO.toFailUpdateDeliveryStatusByCancelOrder().message)
        assertThat(order?.deliveryStatus).isEqualTo(DeliveryStatus.REFUND)
        println("deliveryStatus = ${deliveryStatus?.message}")
    }
}
