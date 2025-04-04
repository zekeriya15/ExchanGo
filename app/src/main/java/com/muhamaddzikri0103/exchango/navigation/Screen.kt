package com.muhamaddzikri0103.exchango.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
}