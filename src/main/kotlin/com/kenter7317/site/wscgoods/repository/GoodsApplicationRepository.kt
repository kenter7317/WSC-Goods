package com.kenter7317.site.wscgoods.repository

import com.kenter7317.site.wscgoods.entity.GoodsApplication
import com.kenter7317.site.wscgoods.entity.Goods
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GoodsApplicationRepository : JpaRepository<GoodsApplication, Long> {
    fun existsByEmailAndGoods(email: String, goods: Goods): Boolean
    fun findByEmail(email: String): List<GoodsApplication>
}
