package com.example.safepass_2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.safepass_2026.ui.SafePassScreen
import com.example.safepass_2026.ui.theme.SafePass2026Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SafePass2026Theme {
                SafePassScreen()
            }
        }
    }
}
