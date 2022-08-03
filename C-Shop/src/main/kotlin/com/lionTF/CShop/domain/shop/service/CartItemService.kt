package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.models.Cart
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.models.cartItemDTOToCartItem
import com.lionTF.CShop.domain.shop.repository.CartItemRepository
import com.lionTF.CShop.domain.shop.repository.CartRepository
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartItemService(
    private val cartItemRepository: CartItemRepository,
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository,
) {

    //장바구니 상품 추가 메소드
    @Transactional
    fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO{
        //남은 재고 확인
        val item =itemRepository.getReferenceById(addCartItemDTO.itemId)
        val cart = cartRepository.getCart(addCartItemDTO.memberId)

        //장바구니에 담고자 하는 상품의 재고가 충분하다면
        if(addCartItemDTO.amount < item.amount){
            val cartItemDTO = CartItemDTO(item,cart,addCartItemDTO.amount)
            cartItemRepository.save(cartItemDTOToCartItem(cartItemDTO))
            return setSuccessAddCartItemResultDTO()
        }
        else{
            return setFailAddCartItemResultDTO()
        }

    }
}