package com.example.cardbininfo.binDataClasses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

// Парселизированный Json дата класс с информацией о Bin
@Parcelize
@JsonClass(generateAdapter = true)
data class Bin(
    @Json(name = "number") var number: Number? = null,
    @Json(name = "scheme") var scheme: String? = null,
    @Json(name = "type") var type: String? = null,
    @Json(name = "brand") var brand: String? = null,
    @Json(name = "prepaid") var prepaid: Boolean? = null,
    @Json(name = "country") var country: Country? = null,
    @Json(name = "bank") var bank: Bank? = null
) : Parcelable
