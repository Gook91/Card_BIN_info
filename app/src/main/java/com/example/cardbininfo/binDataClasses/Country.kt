package com.example.cardbininfo.binDataClasses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "numeric") var numeric: String? = null,
    @Json(name = "alpha2") var alpha2: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "emoji") var emoji: String? = null,
    @Json(name = "currency") var currency: String? = null,
    @Json(name = "latitude") var latitude: Float? = null,
    @Json(name = "longitude") var longitude: Float? = null
) : Parcelable
