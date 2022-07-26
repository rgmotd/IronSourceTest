package com.example.ironsourcetest.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActionData(
    @Json(name = "cool_down") val coolDown: Long,
    val enabled: Boolean,
    val priority: Int,
    val type: String,
    @Json(name = "valid_days") val validDays: List<Int>
)