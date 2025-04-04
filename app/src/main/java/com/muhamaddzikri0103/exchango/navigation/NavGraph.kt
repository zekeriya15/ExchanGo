package com.muhamaddzikri0103.exchango.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.muhamaddzikri0103.exchango.ui.screen.AboutScreen
import com.muhamaddzikri0103.exchango.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            MainScreen()
        }
        composable(route = Screen.About.route) {
            AboutScreen()
        }
    }
}