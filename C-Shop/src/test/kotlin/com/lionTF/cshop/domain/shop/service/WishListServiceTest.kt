package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.member.models.MemberRole
import com.lionTF.cshop.domain.shop.repository.MemberRepository
import com.lionTF.cshop.domain.shop.repository.ItemWishListRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.ItemWishListService
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class WishListServiceTest {

    @Autowired
    private val wishListService: ItemWishListService? = null

    @Autowired
    private val wishListRepository: ItemWishListRepository? = null

    @Autowired
    private val adminItemRepository: AdminItemRepository? = null

    @Autowired
    private val memberRepository: MemberRepository? = null

    private var item1: Item? = null
    private var item2: Item? = null
    private var memberTest1: Member? = null
    private var memberTest2: Member? = null

    @BeforeEach
    fun init() {
        val itemTest1 = Item(
            itemName = "test1",
            category = Category.ALCOHOL,
            price = 10000,
            amount = 10,
            degree = 20,
            itemDescription = "상품 설명입니다.",
            itemStatus = true,
        )

        item1 = adminItemRepository?.save(itemTest1)

        val itemTest2 = Item(
            itemName = "test2",
            category = Category.NONALCOHOL,
            price = 20000,
            amount = 30,
            degree = 40,
            itemDescription = "상품 설명입니다.as",
            itemStatus = true,
        )

        item2 = adminItemRepository?.save(itemTest2)

        val member1 = Member(
            memberName = "test1",
            address = "address-test1",
            phoneNumber = "phone-test1",
            id = "id-test1",
            role = MemberRole.MEMBER
        )
        memberTest1 = memberRepository?.save(member1)

        val member2 = Member(
            memberName = "test2",
            address = "address-test2",
            phoneNumber = "phone-test2",
            id = "id-test2",
            role = MemberRole.MEMBER
        )
        memberTest2 = memberRepository?.save(member2)
    }

    @Test
    @DisplayName("찜하기 목록 생성 test")
    fun createWishListTest() {
        //given

        //when
        val wishList = item1?.itemId?.let { memberTest1?.let { it1 -> wishListService?.createWishList(it1.memberId, it) } }

        //then
        assertThat(wishList!!.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCreateItemWishList().httpStatus)
        assertThat(wishList.message).isEqualTo(AdminResponseDTO.toSuccessCreateItemWishList().message)
    }

    @Test
    @DisplayName("찜하기 목록 생성중 존재하지 않는 아이템을 찜할 때 예외 test")
    fun createWishListExceptionTest() {
        //given
        val noContentItemId = 9123123L

        //when
        val wishList = memberTest1?.let { wishListService?.createWishList(it.memberId, noContentItemId) }

        //then
        assertThat(wishList!!.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateWishListByNoContentItemId().httpStatus)
        assertThat(wishList.message).isEqualTo(AdminResponseDTO.toFailCreateWishListByNoContentItemId().message)
    }

    private fun generatePageable(page: Int = 0, pageSize: Int = 2): PageRequest = PageRequest.of(page, pageSize)

    @Test
    @DisplayName("찜 목록 가져오는 test")
    fun getWishListTest() {
        //given
        item1?.itemId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.createWishList(it1, it) } }
        item2?.itemId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.createWishList(it1, it) } }

        val pageable = generatePageable()

        //when
        val wishList = memberTest1?.memberId?.let { wishListService?.getWishList(it, pageable) }

        //then
        assertThat(wishList?.size).isEqualTo(2)
        assertThat(wishList?.content?.get(0)?.itemId).isEqualTo(item1?.itemId)
        assertThat(wishList?.content?.get(1)?.itemId).isEqualTo(item2?.itemId)
    }

    @Test
    @DisplayName("찜 목록에서 삭제 test")
    fun deleteWishListTest() {
        //given
        item1?.itemId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.createWishList(it1, it) } }
        item2?.itemId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.createWishList(it1, it) } }

        val wishLists = wishListRepository?.findAll()

        //when
        wishLists?.get(0)?.itemWishListId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.deleteWishList(it1, it) } }

        //then
    }

    @Test
    @DisplayName("찜 목록에서 존재하지 않는 찜 목록 삭제 test")
    fun deleteWishListExceptionTest() {
        //given
        item1?.itemId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.createWishList(it1, it) } }
        item2?.itemId?.let { memberTest1?.memberId?.let { it1 -> wishListService?.createWishList(it1, it) } }

        val wishLists = wishListRepository?.findAll()

        val noContentWishListId = 9123123L

        //when
        val test = memberTest1?.memberId?.let { wishListService?.deleteWishList(it, noContentWishListId) }

        //then
        println(test?.message)
    }
}