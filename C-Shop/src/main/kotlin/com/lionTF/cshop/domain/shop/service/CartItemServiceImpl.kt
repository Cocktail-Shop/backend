package com.lionTF.cshop.domain.shop.service

import com.lionTF.cshop.domain.admin.controller.dto.DeleteCartItemDTO
import com.lionTF.cshop.domain.shop.controller.dto.*
import com.lionTF.cshop.domain.shop.models.CartItem
import com.lionTF.cshop.domain.shop.repository.CartItemRepository
import com.lionTF.cshop.domain.shop.repository.CartRepository
import com.lionTF.cshop.domain.shop.repository.ItemRepository
import com.lionTF.cshop.domain.shop.service.shopinterface.CartItemService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartItemServiceImpl(
    private val cartItemRepository: CartItemRepository,
    private val itemRepository: ItemRepository,
    private val cartRepository: CartRepository,
) : CartItemService{

    //장바구니에 단일 상품 추가 메소드
    @Transactional
    override fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO{
        val item =itemRepository.getReferenceById(addCartItemDTO.itemId)
        val cart = cartRepository.getCart(addCartItemDTO.memberId)

        if(addCartItemDTO.amount <= 0){
            return AddCartItemResultDTO.setNotPositiveError()
        }

        return if(item.itemStatus){
            if(addCartItemDTO.amount < item.amount){
                val cartItemDTO = CartItemDTO(item,cart,addCartItemDTO.amount)
                cartItemRepository.save(CartItem.fromCartItemDTO(cartItemDTO))
                AddCartItemResultDTO.setSuccessAddCartItemResultDTO()
            } else{
                AddCartItemResultDTO.setAmountFailAddCartItemResultDTO()
            }
        } else{
            AddCartItemResultDTO.setStatusFailAddCartItemResultDTO()
        }

    }

    //장바구니에 칵테일 상품 재료들 추가 메소드
    @Transactional
    override fun addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO{
        val cart = cartRepository.getCart(addCartCocktailItemDTO.memberId)
        val cartItemDTOList: MutableList<CartItemDTO> = mutableListOf()
        val items = addCartCocktailItemDTO.items

        for(item in items){
            val itemInfo = itemRepository.getReferenceById(item.itemId)

            if(item.amount <= 0){
                return AddCartCocktailItemResultDTO.setNotPositiveError()
            }

            if(itemInfo.amount > item.amount){

                if(itemInfo.itemStatus){
                    cartItemDTOList.add(CartItemDTO(itemInfo,cart,item.amount))
                }else{
                    return AddCartCocktailItemResultDTO.setStatusFailAddCartCocktailItemResultDTO()
                }
            }else{
                return AddCartCocktailItemResultDTO.setAmountFailAddCartCocktailItemResultDTO()
            }
        }

        for(cartItemDTO in cartItemDTOList){
            cartItemRepository.save(CartItem.fromCartItemDTO(cartItemDTO))
        }
        return AddCartCocktailItemResultDTO.setSuccessAddCartCocktailItemResultDTO()
    }

    // 장바구니 상품 삭제
    override fun deleteCartItem(deleteCartDTO: DeleteCartItemDTO): CartResponseDTO {
        val existsCart = cartItemRepository.existsById(deleteCartDTO.cartItemId)

        return if (!existsCart) {
            CartResponseDTO.toFailDeleteItemResponseDTO()

        } else {
            val cart = cartRepository.getCart(deleteCartDTO.memberId)
            cart.deleteCartItem(deleteCartDTO.cartItemId)

            CartResponseDTO.toSuccessDeleteItemResponseDTO()
        }
    }

    // 장바구니 상품 조회
    override fun getCart(pageable: Pageable): ResponseSearchCartResultDTO {
        val findCart = cartRepository.findCartInfo(pageable)

        return ResponseSearchCartResultDTO.cartToResponseCartSearchPageDTO(findCart)
    }
}
