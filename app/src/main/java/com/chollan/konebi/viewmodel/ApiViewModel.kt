package com.chollan.konebi.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chollan.konebi.api.ApiConfig
import com.chollan.konebi.response.LaporanItem
import com.chollan.konebi.response.LaporanResponse
import com.chollan.konebi.response.SettingData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiViewModel : ViewModel() {

    companion object {
        const val TAG = "KONEBI API"
    }

    private val _laporan = mutableStateOf(listOf<LaporanItem>())
    val laporan: State<List<LaporanItem>> = _laporan

    private val _SettingData = mutableStateOf(
        SettingData(false, false, 0.0, 0.0, 0.0, 0, 0, 0, 0)
    )
    val settingData: State<SettingData> = _SettingData

    private val _base64Image = mutableStateOf("")
    val base64Image: State<String> = _base64Image

    fun setBase64Image(base64Image: String) {
        _base64Image.value = base64Image
    }

    fun getLaporan() {
        val client = ApiConfig.getApiService().getLaporans()
        client.enqueue(object : Callback<List<LaporanItem>> {
            override fun onResponse(
                call: Call<List<LaporanItem>>,
                response: Response<List<LaporanItem>>
            ) {
                if (response.isSuccessful) {
                    _laporan.value = response.body()!!
                } else {
                    Log.e(TAG, "onFailure Response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<LaporanItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun postLaporan(laporanItem: LaporanItem) {
        val client = ApiConfig.getApiService().postLaporans(laporanItem)
        client.enqueue(object : Callback<LaporanItem> {
            override fun onResponse(
                call: Call<LaporanItem>,
                response: Response<LaporanItem>
            ) {
                if (response.isSuccessful) {
                    Log.d("Laporan", "Berhasil")
                }
            }

            override fun onFailure(call: Call<LaporanItem>, t: Throwable) {
                Log.d("Laporan", "Gagal")
            }
        })
    }

    fun getSettingData() {
        val client = ApiConfig.getApiService().getSettings()
        client.enqueue(object : Callback<SettingData> {
            override fun onResponse(
                call: Call<SettingData>,
                response: Response<SettingData>
            ) {
                if (response.isSuccessful) {
                    _SettingData.value = response.body()!!
                } else {
                    Log.e(TAG, "onFailure Response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SettingData>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}