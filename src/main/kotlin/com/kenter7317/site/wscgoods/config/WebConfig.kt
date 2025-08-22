package com.kenter7317.site.wscgoods.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    // Spring Boot의 Mustache 자동 설정을 사용합니다.
    // application.properties에서 mustache 설정을 처리합니다.
}
