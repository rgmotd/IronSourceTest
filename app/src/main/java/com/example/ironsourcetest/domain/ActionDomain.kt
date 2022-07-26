package com.example.ironsourcetest.domain

data class ActionDomain(
    val coolDown: Long,
    val enabled: Boolean,
    val priority: Int,
    val type: String,
    val validDays: List<Int>,
    val lastShown: Long
)
