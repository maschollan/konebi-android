package com.chollan.konebi.response

import com.google.gson.annotations.SerializedName

data class SettingData(

	@field:SerializedName("pompa")
	val pompa: Boolean,

	@field:SerializedName("konveyor")
	val konveyor: Boolean,

	@field:SerializedName("scale_faktor_1")
	val scaleFaktor1: Double,

	@field:SerializedName("scale_faktor_2")
	val scaleFaktor2: Double,

	@field:SerializedName("scale_faktor_3")
	val scaleFaktor3: Double,

	@field:SerializedName("tare_3")
	val tare3: Int,

	@field:SerializedName("tare_1")
	val tare1: Int,

	@field:SerializedName("tare_2")
	val tare2: Int,

	@field:SerializedName("speed")
	val speed: Int
)
