package com.kenter7317.site.wscgoods

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class WscGoodsApplication

fun main(args: Array<String>) {
    runApplication<WscGoodsApplication>(*args)
}
