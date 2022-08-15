package com.lionTF.CShop.domain.shop.service

import com.lionTF.CShop.domain.admin.controller.dto.DeleteCartItemDTO
import com.lionTF.CShop.domain.shop.controller.dto.*
import com.lionTF.CShop.domain.shop.models.CartItem
import com.lionTF.CShop.domain.shop.repository.CartItemRepository
import com.lionTF.CShop.domain.shop.repository.CartRepository
import com.lionTF.CShop.domain.shop.repository.ItemRepository
import com.lionTF.CShop.domain.shop.service.shopinterface.CartItemService
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
        //남은 재고 확인
        val item =itemRepository.getReferenceById(addCartItemDTO.itemId)
        val cart = cartRepository.getCart(addCartItemDTO.memberId)

        //사용자가 입력한 수가 0보다 큰지 확인
        if(addCartItemDTO.amount <= 0){
            return AddCartItemResultDTO.setNotPositiveError()
        }

        //삭제된 상품이 아니라면
        if(item.itemStatus){
            //장바구니에 담고자 하는 상품의 재고가 충분하다면
            if(addCartItemDTO.amount < item.amount){
                val cartItemDTO = CartItemDTO(item,cart,addCartItemDTO.amount)
                cartItemRepository.save(CartItem.cartItemDTOToCartItem(cartItemDTO))
                return AddCartItemResultDTO.setSuccessAddCartItemResultDTO()
            }
            else{
                return AddCartItemResultDTO.setAmountFailAddCartItemResultDTO()
            }
        }
        else{
            return AddCartItemResultDTO.setStatusFailAddCartItemResultDTO()
        }

    }

    //장바구니에 칵테일 상품 재료들 추가 메소드
    @Transactional
    override fun addCartCocktailItem(addCartCocktailItemDTO: AddCartCocktailItemDTO) : AddCartCocktailItemResultDTO{
        val errorItems: MutableList<Long> = mutableListOf()
        val cart = cartRepository.getCart(addCartCocktailItemDTO.memberId)
        val cartItemDTOList: MutableList<CartItemDTO> = mutableListOf()
        val items = addCartCocktailItemDTO.items
        var isAmountEnough: Boolean = true
        var isNotDeleted: Boolean = true
        var isPositive: Boolean = true
        for(item in items){
            val itemInfo = itemRepository.getReferenceById(item.itemId)
            //사용자가 요청한 수량이 양수가 아니면
            if(item.amount <= 0){
                isPositive = false
                break
            }

            //재고가 충분하면
            if(itemInfo.amount > item.amount){
                //삭제된 상품이 아닌지 확인
                if(itemInfo.itemStatus){
                    cartItemDTOList.add(CartItemDTO(itemInfo,cart,item.amount))

                }else{
                    isNotDeleted = false
                    errorItems.add(item.itemId)
                }
            }else{
                isAmountEnough = false
                errorItems.add(item.itemId)
            }

        }

        //요청한 상품중에 수량이 양수가 아닌 수가 있는 경우
        if(!isPositive){
            return AddCartCocktailItemResultDTO.setNotPositiveError(errorItems)
        }

        //재고가 부족한 상품이 있거나 삭제된 상품이 있는지 확인하여 처리
        if(isNotDeleted and isAmountEnough){
            for(cartItemDTO in cartItemDTOList){
                cartItemRepository.save(CartItem.cartItemDTOToCartItem(cartItemDTO))
            }
            return AddCartCocktailItemResultDTO.setSuccessAddCartCocktailItemResultDTO(errorItems)
        }
        else{
            if(isNotDeleted){
                return AddCartCocktailItemResultDTO.setStatusFailAddCartCocktailItemResultDTO(errorItems)
            }
            else{
                return AddCartCocktailItemResultDTO.setAmountFailAddCartCocktailItemResultDTO(errorItems)
            }
        }
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