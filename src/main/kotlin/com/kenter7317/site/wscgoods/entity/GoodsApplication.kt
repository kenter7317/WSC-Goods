package com.kenter7317.site.wscgoods.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "goods_application")
data class GoodsApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val email: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    val goods: Goods,

    @Column(nullable = false)
    val appliedAt: LocalDateTime = LocalDateTime.now()
)
