package com.example.apprender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apprender.navigation.AppNavigation
import com.example.apprender.ui.theme.ApprenderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApprenderTheme {
                AppNavigation()
            }
        }
    }
}
