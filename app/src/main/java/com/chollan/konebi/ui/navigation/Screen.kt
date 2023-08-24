package com.chollan.konebi.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("Monitoring")
    object Splash: Screen("splash")
    object Report: Screen("Laporan")
    object Control: Screen("Kontrol")
}