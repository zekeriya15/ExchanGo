package com.muhamaddzikri0103.exchango

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muhamaddzikri0103.exchango.ui.screen.MainScreen
import com.muhamaddzikri0103.exchango.ui.theme.ExchanGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchanGoTheme {
                MainScreen()
            }
        }
    }
}