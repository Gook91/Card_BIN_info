package com.example.cardbininfo.binDataClasses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Bank(
    @Json(name = "name") var name: String? = null,
    @Json(name = "url") var url: String? = null,
    @Json(name = "phone") var phone: String? = null,
    @Json(name = "city") var city: String? = null
) : Parcelable
