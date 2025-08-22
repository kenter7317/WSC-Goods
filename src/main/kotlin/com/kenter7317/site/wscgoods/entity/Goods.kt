package com.kenter7317.site.wscgoods.entity

import jakarta.persistence.*

@Entity
@Table(name = "goods")
data class Goods(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    var stock: Int,

    @Column(nullable = false)
    val imageUrl: String = ""
)
