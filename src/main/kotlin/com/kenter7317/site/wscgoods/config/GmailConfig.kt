package com.kenter7317.site.wscgoods.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "gmail")
data class GmailConfig(
    var serviceAccountKeyPath: String = "",
    var applicationName: String = "",
    var fromEmail: String = ""
)
