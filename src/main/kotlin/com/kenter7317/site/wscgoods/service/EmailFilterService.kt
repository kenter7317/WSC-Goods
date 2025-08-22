package com.kenter7317.site.wscgoods.service

import org.springframework.stereotype.Service

@Service
class EmailFilterService {

    // 허용된 이메일 리스트 (나중에 사용자가 제공할 예정)
    private val allowedEmails = mutableSetOf<String>()

    fun updateAllowedEmails(emails: List<String>) {
        allowedEmails.clear()
        allowedEmails.addAll(emails.map { it.lowercase().trim() })
    }

    fun isEmailAllowed(email: String): Boolean {
        return allowedEmails.contains(email.lowercase().trim())
    }

    fun getAllowedEmails(): Set<String> {
        return allowedEmails.toSet()
    }
}
