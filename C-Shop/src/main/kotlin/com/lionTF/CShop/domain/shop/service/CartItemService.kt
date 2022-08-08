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

    //장바구니에 단일 상품 추가 메소드
    @Transactional
    fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO{
        //남은 재고 확인
        val item =itemRepository.getReferenceById(addCartItemDTO.itemId)
        val cart = cartRepository.getCart(addCartItemDTO.memberId)

        //삭제된 상품이 아니라면
        if(item.itemStatus){
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
        else{
            return setFailAddCartItemResultDTO()
        }

    }

        //장바구니에 칵테일 상품 재료들 추가 메소드
    @Transactional
    fun  addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO{
        val errorItems: MutableList<Long> = mutableListOf()
        val cart = cartRepository.getCart(addCartCocktailItemDTO.memberId)
        val cartItemDTOList: MutableList<CartItemDTO> = mutableListOf()
        val items = addCartCocktailItemDTO.items
        var canAddToCart: Boolean = true

        for(item in items){
            val itemInfo = itemRepository.getReferenceById(item.itemId)
            //재고가 충분하면
            if(itemInfo.amount > item.amount){
                //삭제된 상품이 아닌지 확인
                if(itemInfo.itemStatus){
                    cartItemDTOList.add(CartItemDTO(itemInfo,cart,item.amount))

                }else{
                    canAddToCart = false
                    errorItems.add(item.itemId)
                }
            }else{
                canAddToCart = false
                errorItems.add(item.itemId)
            }

        }

        //재고가 부족한 상품이 있거나 삭제된 상품이 있는지 확인하여 처리
        if(canAddToCart){
            for(cartItemDTO in cartItemDTOList){
                cartItemRepository.save(cartItemDTOToCartItem(cartItemDTO))
            }
            return setSuccessAddCartCocktailItemResultDTO(errorItems)
        }
        else{
            return setFailAddCartCocktailItemResultDTO(errorItems)
        }
    }
}