package com.kenter7317.site.wscgoods.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "email_verification")
data class EmailVerification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val code: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var isVerified: Boolean = false,

    @Column(nullable = false)
    val expiresAt: LocalDateTime = LocalDateTime.now().plusMinutes(10)
)
