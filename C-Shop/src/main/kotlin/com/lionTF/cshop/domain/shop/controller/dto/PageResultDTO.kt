package com.lionTF.cshop.domain.shop.controller.dto

import com.lionTF.cshop.domain.admin.models.Cocktail
import com.lionTF.cshop.domain.admin.models.Item
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import java.util.function.Function
import java.util.stream.IntStream
import kotlin.math.ceil
import kotlin.streams.toList

data class ItemPageResponseDTO(
    val status: Int,
    val message: String,
    val result: PageResultDTO<SearchItemInfoDTO, Item>
) {

    companion object {
        fun setItemPageResponseDTO(pageResult: PageResultDTO<SearchItemInfoDTO, Item>): ItemPageResponseDTO {
            return ItemPageResponseDTO(
                status = HttpStatus.OK.value(),
                message = "상품 조회 성공",
                result = pageResult
            )
        }
    }
}

data class CocktailPageResponseDTO(
    val status: Int,
    val message: String,
    val result: PageResultDTO<SearchCocktailInfoDTO, Cocktail>
) {

    companion object {
        fun setCocktailPageResponseDTO(pageResult: PageResultDTO<SearchCocktailInfoDTO, Cocktail>): CocktailPageResponseDTO {
            return CocktailPageResponseDTO(
                status = HttpStatus.OK.value(),
                message = "칵테일 상품 조회 성공",
                result = pageResult
            )
        }
    }
}

data class PageResultDTO<DTO, EN>(
    var data: List<DTO>? = null,
    var totalPage: Int? = null,
    var page: Int = 0,
    var size: Int = 0,
    var start: Int = 0,
    var end: Int = 0,
    var prev: Boolean? = null,
    var next: Boolean? = null,
    var pageList: MutableList<Int>? = null,
) {

    constructor(result: Page<EN>, fn: Function<EN, DTO>) : this() {
        data = result.map(fn).toList()
        totalPage = result.totalPages
        makePageList(result.pageable)

    }

    private fun makePageList(pageable: Pageable) {
        this.page = pageable.pageNumber + 1
        this.size = pageable.pageSize

        val tempEnd: Int = ((ceil(page / 10.0)) * 10).toInt()
        start = tempEnd - 9
        prev = start > 1

        next = totalPage!! > tempEnd

        if (totalPage!! > tempEnd) end = tempEnd
        else end = totalPage!!

        pageList = IntStream.rangeClosed(start, end).boxed().toList() as MutableList<Int>
    }
}
