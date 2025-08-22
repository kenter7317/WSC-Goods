package com.kenter7317.site.wscgoods.config

import com.kenter7317.site.wscgoods.entity.Goods
import com.kenter7317.site.wscgoods.repository.GoodsRepository
import com.kenter7317.site.wscgoods.service.EmailFilterService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer(
    private val goodsRepository: GoodsRepository,
    private val emailFilterService: EmailFilterService
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // 샘플 굿즈 데이터 추가
        if (goodsRepository.count() == 0L) {
            val sampleGoods = listOf(
                Goods(
                    name = "WSC 티셔츠",
                    description = "고품질 면 100% WSC 로고 티셔츠",
                    stock = 50
                ),
                Goods(
                    name = "WSC 머그컵",
                    description = "WSC 로고가 새겨진 세라믹 머그컵",
                    stock = 30
                ),
                Goods(
                    name = "WSC 에코백",
                    description = "친환경 캔버스 소재의 WSC 에코백",
                    stock = 25
                ),
                Goods(
                    name = "WSC 스티커팩",
                    description = "다양한 WSC 스티커 세트",
                    stock = 100
                ),
                Goods(
                    name = "WSC 노트북",
                    description = "WSC 로고가 인쇄된 고급 노트북",
                    stock = 20
                )
            )

            goodsRepository.saveAll(sampleGoods)
            println("샘플 굿즈 데이터가 초기화되었습니다.")
        }

        // 샘플 허용 이메일 추가 (테스트용)
        val sampleEmails = listOf(
            "test@example.com",
            "user@test.com",
            "admin@wsc.com"
        )
        emailFilterService.updateAllowedEmails(sampleEmails)
        println("샘플 허용 이메일이 설정되었습니다: $sampleEmails")
    }
}
