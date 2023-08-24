package com.chollan.konebi.response

import com.google.gson.annotations.SerializedName

data class LaporanResponse(
	@field:SerializedName("Response")
	val response: List<LaporanItem>
)

data class LaporanItem(

	@field:SerializedName("jumlah")
	val jumlah: String,

	@field:SerializedName("berat")
	val berat: String,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("id")
	val id: Int? = null,
)
