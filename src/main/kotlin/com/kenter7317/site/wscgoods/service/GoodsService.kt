package com.kenter7317.site.wscgoods.service

import com.kenter7317.site.wscgoods.entity.GoodsApplication
import com.kenter7317.site.wscgoods.repository.GoodsApplicationRepository
import com.kenter7317.site.wscgoods.repository.GoodsRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GoodsService(
    private val goodsRepository: GoodsRepository,
    private val goodsApplicationRepository: GoodsApplicationRepository
) {

    fun getAvailableGoods() = goodsRepository.findByStockGreaterThan(0)

    @Transactional
    fun applyForGoods(email: String, goodsId: Long): String {
        val goods = goodsRepository.findById(goodsId).orElse(null)
            ?: return "굿즈를 찾을 수 없습니다."

        if (goods.stock <= 0) {
            return "재고가 부족합니다."
        }

        if (goodsApplicationRepository.existsByEmailAndGoods(email, goods)) {
            return "이미 신청하신 굿즈입니다."
        }

        // 재고 감소
        goods.stock -= 1
        goodsRepository.save(goods)

        // 신청 내역 저장
        val application = GoodsApplication(
            email = email,
            goods = goods
        )
        goodsApplicationRepository.save(application)

        return "신청이 완료되었습니다."
    }

    fun getUserApplications(email: String) = goodsApplicationRepository.findByEmail(email)
}
