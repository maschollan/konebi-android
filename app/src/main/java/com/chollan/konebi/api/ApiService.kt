package com.chollan.konebi.api

import com.chollan.konebi.response.LaporanItem
import com.chollan.konebi.response.LaporanResponse
import com.chollan.konebi.response.SettingData
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @GET("laporan")
    fun getLaporans(): Call<List<LaporanItem>>

    @POST("laporan")
    fun postLaporans(@Body laporan: LaporanItem): Call<LaporanItem>

    @GET("settings")
    fun getSettings(): Call<SettingData>

    @POST("settings")
    fun postSettings(@Body setting: SettingData): Call<SettingData>
}