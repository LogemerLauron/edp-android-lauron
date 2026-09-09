package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.ui.AppViewModelFactory
import com.example.myapplication.ui.MySocialApp
import com.example.myapplication.ui.PostsViewModel
import com.example.myapplication.ui.ThemeViewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val factory = AppViewModelFactory(applicationContext)
        val postsVm: PostsViewModel by viewModels { factory }
        val themeVm: ThemeViewModel by viewModels { factory }

        setContent {
            val isDarkTheme by themeVm.isDarkTheme.collectAsStateWithLifecycle()
            
            MyApplicationTheme(darkTheme = isDarkTheme, dynamicColor = false) {
                MySocialApp(postsVm, themeVm)
            }
        }
    }
}
