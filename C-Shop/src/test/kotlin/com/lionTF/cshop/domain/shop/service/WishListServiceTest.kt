package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.AdminResponseDTO
import com.lionTF.cshop.domain.admin.models.Category
import com.lionTF.cshop.domain.admin.models.Item
import com.lionTF.cshop.domain.admin.repository.AdminItemRepository
import com.lionTF.cshop.domain.member.models.Member
import com.lionTF.cshop.domain.shop.repository.MemberRepository
import com.lionTF.cshop.domain.shop.repository.WishListRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.WishListService
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
    private val wishListService: WishListService? = null

    @Autowired
    private val wishListRepository: WishListRepository? = null

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
            id = "id-test1"
        )
        memberTest1 = memberRepository?.save(member1)

        val member2 = Member(
            memberName = "test2",
            address = "address-test2",
            phoneNumber = "phone-test2",
            id = "id-test2"
        )
        memberTest2 = memberRepository?.save(member2)
    }

    @Test
    @DisplayName("찜하기 목록 생성 test")
    fun createWishListTest() {
        //given

        //when
        val wishList = item1?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }

        //then
        assertThat(wishList!!.httpStatus).isEqualTo(AdminResponseDTO.toSuccessCreateWishList().httpStatus)
        assertThat(wishList.message).isEqualTo(AdminResponseDTO.toSuccessCreateWishList().message)
    }

    @Test
    @DisplayName("찜하기 목록 생성중 존재하지 않는 아이템을 찜할 때 예외 test")
    fun createWishListExceptionTest() {
        //given
        val noContentItemId = 9123123L

        //when
        val wishList = wishListService?.createWishList(memberTest1?.memberId, noContentItemId)

        //then
        assertThat(wishList!!.httpStatus).isEqualTo(AdminResponseDTO.toFailCreateWishListByNoContentItemId().httpStatus)
        assertThat(wishList.message).isEqualTo(AdminResponseDTO.toFailCreateWishListByNoContentItemId().message)
    }

    private fun generatePageable(page: Int = 0, pageSize: Int = 2): PageRequest = PageRequest.of(page, pageSize)

    @Test
    @DisplayName("찜 목록 가져오는 test")
    fun getWishListTest() {
        //given
        item1?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }
        item2?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }

        val pageable = generatePageable()

        //when
        val wishList = wishListService?.getWishList(memberTest1?.memberId, pageable)

        //then
        assertThat(wishList?.size).isEqualTo(2)
        assertThat(wishList?.content?.get(0)?.itemId).isEqualTo(item1?.itemId)
        assertThat(wishList?.content?.get(1)?.itemId).isEqualTo(item2?.itemId)
    }

    @Test
    @DisplayName("찜 목록에서 삭제 test")
    fun deleteWishListTest() {
        //given
        item1?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }
        item2?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }

        val wishLists = wishListRepository?.findAll()

        //when
        wishLists?.get(0)?.wishListId?.let { wishListService?.deleteWishList(memberTest1?.memberId, it) }

        //then
        assertThat(wishLists?.get(0)?.wishListStatus).isEqualTo(false)
    }

    @Test
    @DisplayName("찜 목록에서 존재하지 않는 찜 목록 삭제 test")
    fun deleteWishListExceptionTest() {
        //given
        item1?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }
        item2?.itemId?.let { wishListService?.createWishList(memberTest1?.memberId, it) }

        val wishLists = wishListRepository?.findAll()

        val noContentWishListId = 9123123L

        //when
        val test = wishListService?.deleteWishList(memberTest1?.memberId, noContentWishListId)

        //then
        println(test?.message)
        assertThat(wishLists?.get(3)?.wishListStatus).isEqualTo(true)
    }
}