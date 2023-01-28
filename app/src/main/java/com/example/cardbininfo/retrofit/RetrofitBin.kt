package com.example.cardbininfo.retrofit

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Объект retrofit для подключения к серверу
object RetrofitBin {
    private const val BASE_URL = "https://lookup.binlist.net"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val binInfoApi: BinInfoApi = retrofit.create(BinInfoApi::class.java)
}
