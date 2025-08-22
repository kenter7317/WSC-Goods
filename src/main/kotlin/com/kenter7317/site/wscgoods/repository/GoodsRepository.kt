package com.kenter7317.site.wscgoods.repository

import com.kenter7317.site.wscgoods.entity.Goods
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GoodsRepository : JpaRepository<Goods, Long> {
    fun findByStockGreaterThan(stock: Int): List<Goods>
}
