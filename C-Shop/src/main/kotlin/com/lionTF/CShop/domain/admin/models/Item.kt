package com.lionTF.CShop.domain.admin.models

import com.lionTF.CShop.domain.admin.controller.dto.ItemResultDTO
import com.lionTF.CShop.domain.admin.controller.dto.RequestCreateItemDTO
import com.lionTF.CShop.domain.admin.controller.dto.ResponseItemDTO
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.OrderItem
import com.lionTF.CShop.global.model.BaseTimeEntity
import javax.persistence.*

@Entity
@EntityListeners
class Item(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val itemId: Long = 0,

    @OneToMany(mappedBy = "item")
    var cocktailItem: List<CocktailItem>? = null,

    @OneToMany(mappedBy = "item")
    var cartItem: List<CartItem>? = null,

    @OneToMany(mappedBy = "item")
    var orderItem: List<OrderItem>? = null,

    var itemName: String,

    var price: Int = 0,
    var amount: Int = 0,
    var degree: Int = 0,
    var itemDescription: String ="",
    var itemImgUrl: String = "",

    @Enumerated(EnumType.STRING)
    var category: Category,

    var itemStatus: Boolean,
) :BaseTimeEntity(){

    companion object {
        fun requestCreateItemDTOtoItem (requestCreateItemDTO: RequestCreateItemDTO): Item {
            return Item(
                itemName = requestCreateItemDTO.itemName,
                category = requestCreateItemDTO.category,
                price = requestCreateItemDTO.price,
                amount = requestCreateItemDTO.amount,
                degree = requestCreateItemDTO.degree,
                itemDescription = requestCreateItemDTO.itemDescription,
                itemStatus = true,
            )
        }
    }

    // 상품 수정
    fun update(requestCreateItemDTO: RequestCreateItemDTO) {
        itemName = requestCreateItemDTO.itemName
        category = requestCreateItemDTO.category
        price = requestCreateItemDTO.price
        amount = requestCreateItemDTO.amount
        degree = requestCreateItemDTO.degree
        itemDescription = requestCreateItemDTO.itemDescription
    }


    // 상품 삭제
    fun delete() {
        itemStatus = false
    }

    // 주문 취소 후 수량 복귀
    fun addStock(quantity: Int) {
        amount += quantity
    }
}
