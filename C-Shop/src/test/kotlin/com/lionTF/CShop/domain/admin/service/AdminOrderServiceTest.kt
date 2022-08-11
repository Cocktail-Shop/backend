package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.models.Category
import com.lionTF.CShop.domain.admin.models.Item
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminOrderService
import com.lionTF.CShop.domain.member.models.Member
import com.lionTF.CShop.domain.member.models.MemberRole
import com.lionTF.CShop.domain.member.repository.MemberAuthRepository
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderDTO
import com.lionTF.CShop.domain.shop.controller.dto.RequestOrderItemDTO
import com.lionTF.CShop.domain.shop.models.OrderStatus
import com.lionTF.CShop.domain.shop.models.Orders
import com.lionTF.CShop.domain.shop.repository.OrderRepository
import com.lionTF.CShop.domain.shop.service.OrderService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
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

    private var order: Orders? = null
    private var item1: Item? = null
    private var item2: Item? = null
    private var item3: Item? = null
    private var memberTest1: Member? = null
    private var memberTest2: Member? = null
    private var memberTest3: Member? = null

    @BeforeEach
    fun init() {
        var orderDTO = Orders(
            orderAddress = "test"
        )

        order = orderRepository.save(orderDTO)

        val createItemDTO1 = RequestCreateItemDTO(
            itemName = "test1",

            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item1 = adminItemRepository.save(itemDTOToItem(createItemDTO1))

        val createItemDTO2 = RequestCreateItemDTO(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item2 = adminItemRepository.save(itemDTOToItem(createItemDTO2))

        val createItemDTO3 = RequestCreateItemDTO(
            itemName = "test2",
            category = Category.ALCOHOL,
            price = 1,
            amount = 10,
            degree = 10,
            itemDescription = "test"
        )
        item3 = adminItemRepository.save(itemDTOToItem(createItemDTO3))


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
            orderAddress = "test-address"
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
            orderAddress = "test-address"
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
            orderAddress = "test-address"
        )
        orderService.requestOrder(requestOrderDTO3)
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

    @Test
    @DisplayName("하나의 주문 취소 test")
    fun deleteOneOrderTest() {
        //given

        //when
        val deleteOneOrder = adminOrderService.cancelOneOrder(order!!.orderId)

        //then
        assertThat(deleteOneOrder.status).isEqualTo(setDeleteSuccessOrdersResultDTO().status)
        assertThat(deleteOneOrder.message).isEqualTo(setDeleteSuccessOrdersResultDTO().message)
        assertThat(order!!.orderStatus).isEqualTo(OrderStatus.CANCEL)
    }
    
    @Test
    @DisplayName("하나의 주문 취소 예외 test")
    fun deleteOneOrderExceptionTest() {
        //given
        val orderId: Long = 98L

        //then
        assertThrows<JpaObjectRetrievalFailureException> { adminOrderService.cancelOneOrder(orderId) }
    } 

    @Test
    @DisplayName("주문 전체 조회 test")
    fun getAllOrdersTest() {
        //given
        val orderCount = orderRepository.count()

        //when
        val allOrders = adminOrderService.getAllOrders(Pageable.unpaged())

        //then
        assertThat(allOrders.content.size).isEqualTo(orderCount-1)
        assertThat(allOrders.content[0].memberId).isEqualTo(memberTest1?.memberId)
        assertThat(allOrders.content[0].itemId).isEqualTo(item1?.itemId)
        assertThat(allOrders.content[1].memberId).isEqualTo(memberTest2?.memberId)
        assertThat(allOrders.content[1].itemId).isEqualTo(item2?.itemId)
        assertThat(allOrders.content[2].memberId).isEqualTo(memberTest3?.memberId)
        assertThat(allOrders.content[2].itemId).isEqualTo(item3?.itemId)
    }

    @Test
    @DisplayName("회원 ID로 주문 조회 test")
    fun getOrdersByMemberIdTest() {
        //given
        val keyword1: String = "te"
        val keyword2: String = "le"
        val keyword3: String = "e"

        //when
        val ordersByMemberId1 = adminOrderService.getOrdersByMemberId(keyword1, Pageable.unpaged())
        val ordersByMemberId2 = adminOrderService.getOrdersByMemberId(keyword2, Pageable.unpaged())
        val ordersByMemberId3 = adminOrderService.getOrdersByMemberId(keyword3, Pageable.unpaged())

        //then
        assertThat(ordersByMemberId1.content.size).isEqualTo(2)
        assertThat(ordersByMemberId1.content[0].memberId).isEqualTo(memberTest1?.memberId)
        assertThat(ordersByMemberId1.content[0].itemId).isEqualTo(item1?.itemId)
        assertThat(ordersByMemberId1.content[1].memberId).isEqualTo(memberTest2?.memberId)
        assertThat(ordersByMemberId1.content[1].itemId).isEqualTo(item2?.itemId)
        assertThat(ordersByMemberId2.content.size).isEqualTo(1)
        assertThat(ordersByMemberId3.content.size).isEqualTo(3)
    }
}