package com.lionTF.CShop.domain.admin.service

import com.lionTF.CShop.domain.admin.controller.dto.*
import com.lionTF.CShop.domain.admin.repository.AdminItemRepository
import com.lionTF.CShop.domain.admin.service.admininterface.AdminItemService
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class AdminItemServiceImpl(

    private val adminItemRepository: AdminItemRepository,

): AdminItemService {

    override fun createItem(createItemDTO: createItemDTO): createItemResultDTO {
        val item = itemToItem(createItemDTO)
        adminItemRepository.save(item)

        item.itemId?.let {
            return when (adminItemRepository.existsById(it)) {
                true -> {
                    setCreateSuccessItemResultDTO()
                }
                else -> {
                    setCreateFailItemResultDTO()
                }
            }
        }

        return setCreateSuccessItemResultDTO()
    }
}