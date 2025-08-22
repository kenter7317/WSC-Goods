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
        // DB에 굿즈가 없을 때만 샘플 데이터 추가
        if (goodsRepository.count() == 0L) {
            val sampleGoods = listOf(
                Goods(
                    name = "WSC 클립펜",
                    description = "WSC 로고 클립펜 (블랙x2, 베이지x2, 민트x2)",
                    stock = 6
                ),
                Goods(
                    name = "JCJS games 투명 아크릴 키링",
                    description = "JCJS games 투명 아크릴 키링",
                    stock = 2
                ),
                Goods(
                    name = "실 제본 무지노트",
                    description = "실 제본 무지노트",
                    stock = 6
                ),
                Goods(
                    name = "웨루 그림 스티커",
                    description = "웨루 그림 스티커",
                    stock = 6
                ),
                Goods(
                    name = "WSC 북마크",
                    description = "WSC 북마크",
                    stock = 4
                ),
                Goods(
                    name = "웨루 키캡 R4",
                    description = "웨루 키캡 R4",
                    stock = 3
                ),
                Goods(
                    name = "에폭시 사각 스마트톡",
                    description = "에폭시 사각 스마트톡 (개발이장난이냐x1, 머리쥐뜯는웨루x1)",
                    stock = 2
                ),
                Goods(
                    name = "헛소리 채널 정사각형 마우스패드",
                    description = "헛소리 채널 정사각형 마우스패드",
                    stock = 2
                ),
                Goods(
                    name = "15.6인치 노트북 파우치",
                    description = "15.6인치 노트북 파우치",
                    stock = 5
                ),
                Goods(
                    name = "파우치",
                    description = "파우치",
                    stock = 5
                ),
                Goods(
                    name = "머그컵",
                    description = "머그컵",
                    stock = 5
                ),
                Goods(
                    name = "유니티 크록스 블랙",
                    description = "유니티 크록스 블랙",
                    stock = 5
                ),
                Goods(
                    name = "에코백",
                    description = "에코백",
                    stock = 5
                )
            )

            goodsRepository.saveAll(sampleGoods)
            println("WSC 굿즈 데이터가 DB에 초기화되었습니다. (총 ${sampleGoods.size}개)")
        } else {
            println("기존 굿즈 데이터가 DB에 존재합니다. (총 ${goodsRepository.count()}개)")
        }

        // 샘플 허용 이메일 추가 (테스트용)
        val sampleEmails = listOf(
            "test@example.com",
            "user@test.com",
            "admin@wsc.com",
            "kenter7317@gmail.com"
        )
        emailFilterService.updateAllowedEmails(sampleEmails)
        println("허용된 이메일 목록이 설정되었습니다: $sampleEmails")
    }
}
