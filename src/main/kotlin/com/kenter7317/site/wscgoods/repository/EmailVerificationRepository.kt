package com.kenter7317.site.wscgoods.repository

import com.kenter7317.site.wscgoods.entity.EmailVerification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface EmailVerificationRepository : JpaRepository<EmailVerification, Long> {
    fun findByEmailAndCodeAndIsVerifiedFalseAndExpiresAtAfter(
        email: String,
        code: String,
        now: LocalDateTime
    ): EmailVerification?

    fun findByEmailAndIsVerifiedFalse(email: String): List<EmailVerification>
}
