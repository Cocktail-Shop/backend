package com.lionTF.cshop.domain.shop.service

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
) : CartItemService {

    @Transactional
    override fun addCartItem(addCartItemDTO: AddCartItemDTO): AddCartItemResultDTO {
        val item = itemRepository.getReferenceById(addCartItemDTO.itemId)
        val cart = cartRepository.getCart(addCartItemDTO.memberId)

        if (addCartItemDTO.amount <= 0) {
            return AddCartItemResultDTO.setNotPositiveError()
        }

        return if (item.itemStatus) {
            if (addCartItemDTO.amount <= item.amount) {
                val cartItemDTO = CartItemDTO(item, cart, addCartItemDTO.amount)
                cartItemRepository.save(CartItem.fromCartItemDTO(cartItemDTO))
                AddCartItemResultDTO.setSuccessAddCartItemResultDTO()
            } else {
                AddCartItemResultDTO.setAmountFailAddCartItemResultDTO()
            }
        } else {
            AddCartItemResultDTO.setStatusFailAddCartItemResultDTO()
        }

    }

    @Transactional
    override fun addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO): AddCartCocktailItemResultDTO {
        val cart = cartRepository.getCart(addCartCocktailItemDTO.memberId)
        val cartItemDTOList: MutableList<CartItemDTO> = mutableListOf()
        val items = addCartCocktailItemDTO.items
        var zeroAmountCount = 0
        items.map {
            val itemInfo = itemRepository.getReferenceById(it.itemId)

            if (it.amount == 0) zeroAmountCount++

            if (it.amount < 0) {
                return AddCartCocktailItemResultDTO.setNotPositiveError()
            }

            if (itemInfo.amount >= it.amount) {
                if (itemInfo.itemStatus) {
                    if (it.amount > 0) cartItemDTOList.add(CartItemDTO(itemInfo, cart, it.amount))
                } else {
                    return AddCartCocktailItemResultDTO.setStatusFailAddCartCocktailItemResultDTO()
                }
            } else {
                return AddCartCocktailItemResultDTO.setAmountFailAddCartCocktailItemResultDTO()
            }
        }

        if (zeroAmountCount == items.size)
            return AddCartCocktailItemResultDTO.setAllZeroFailAddCartCocktailItemResultDTO()

        cartItemDTOList.map { cartItemDTO ->
            cartItemRepository.save(CartItem.fromCartItemDTO(cartItemDTO))
        }

        return AddCartCocktailItemResultDTO.setSuccessAddCartCocktailItemResultDTO()
    }

    @Transactional
    override fun deleteCartItem(cartItemId: Long): CartResponseDTO {
        cartItemRepository.deleteCartItem(cartItemId)
        return CartResponseDTO.toSuccessDeleteItemResponseDTO()
    }

    override fun getCart(memberId: Long, pageable: Pageable): ResponseSearchCartResultDTO {
        val findCart = cartRepository.findCartInfo(memberId, pageable)
        return ResponseSearchCartResultDTO.cartToResponseCartSearchPageDTO(findCart)
    }
}
