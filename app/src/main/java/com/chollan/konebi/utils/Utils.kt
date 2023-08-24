package com.chollan.konebi.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import com.chollan.konebi.response.LaporanItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


data class CombinedData(
    val berat: List<Int>,
    val jumlah: List<Int>
)

fun <T> List<List<T>>.transpose(): List<List<T>> {
    return if (isEmpty()) {
        emptyList()
    } else {
        val width = first().size
        (0 until width).map { col -> map { it[col] } }
    }
}

fun List<LaporanItem>.transformData(): List<Map<String, String>> {


    val combinedData = mutableMapOf<String, CombinedData>()

    this.forEach { item ->
        val beratValues = item.berat.split('|').map { it.toInt() }
        val jumlahValues = item.jumlah.split('|').map { it.toInt() }

        val date = item.createdAt.split("T")[0]

        if (!combinedData.containsKey(date)) {
            combinedData[date] = CombinedData(
                berat = beratValues,
                jumlah = jumlahValues
            )
        } else {
            combinedData[date]?.let { existingData ->
                combinedData[date] = CombinedData(
                    berat = existingData.berat.zip(beratValues).map { (a, b) -> a + b },
                    jumlah = existingData.jumlah.zip(jumlahValues).map { (a, b) -> a + b }
                )
            }
        }
    }

    return combinedData.map { (date, data) ->
        mapOf(
            "created_at" to date,
            "berat" to data.berat.joinToString("|"),
            "jumlah" to data.jumlah.joinToString("|")
        )
    }
}

fun List<Map<String, String>>.toBerat(): List<List<Int>> {
    val listBerat = this.map {
        val beratValues = it["berat"]!!.split('|').map { it.toInt() }
        beratValues
    }.transpose().mapIndexed { index, data ->
        data
    }

    return listBerat
}

fun List<Map<String, String>>.toJumlah(): List<List<Int>> {
    val listJumlah = this.map {
        val jumlahValues = it["jumlah"]!!.split('|').map { it.toInt() }
        jumlahValues
    }.transpose().mapIndexed { index, data ->
        data
    }

    return listJumlah
}

fun List<Map<String, String>>.toLabels(): List<String> {
    return this.map { it["created_at"]!! }
}

// convert string 2023-07-13 to 13 Juli
@RequiresApi(Build.VERSION_CODES.O)
fun String.toSimpleDate(): String {
    val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val formatterOutput = DateTimeFormatter.ofPattern("d MMMM", Locale("id", "ID"))

    val date = LocalDate.parse(this, formatterInput)
    return date.format(formatterOutput)
}

fun decodeBase64ToBitmap(base64String: String): Bitmap {
    val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

fun String.beratTransform(): List<String> {
    var berat = ""
    var beratSatuan = ""
    if (this.toInt() > 1000) {
        berat = String.format("%.1f", (this.toDouble() / 1000))
        beratSatuan = "kg"
    } else {
        berat = this
        beratSatuan = "gram"
    }
    return listOf(berat, beratSatuan)
}