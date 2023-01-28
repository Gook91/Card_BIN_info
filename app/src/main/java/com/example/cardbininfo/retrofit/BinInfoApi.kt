package com.example.cardbininfo.retrofit

import com.example.cardbininfo.binDataClasses.Bin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

// Интерфейс для получения информации о Bin с сайта
interface BinInfoApi {
    @Headers(
        "Accept-Version:3"
    )
    @GET("/{binNumber}")
    fun getBinInfo(@Path("binNumber") binNumber: String): Call<Bin>
}