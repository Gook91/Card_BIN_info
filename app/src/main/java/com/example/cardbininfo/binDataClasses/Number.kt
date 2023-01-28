package com.example.cardbininfo.binDataClasses

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Number(
    @Json(name = "length") var length: Int? = null,
    @Json(name = "luhn") var luhn: Boolean? = null
) : Parcelable
