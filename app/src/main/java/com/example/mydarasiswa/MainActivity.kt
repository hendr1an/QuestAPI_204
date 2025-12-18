package com.example.mydarasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mydarasiswa.ui.theme.MyDaraSiswaTheme
import com.example.mydarasiswa.uicontroller.DataSiswaApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // HAPUS enableEdgeToEdge() KARENA SERING BIKIN CRASH COMPILER DI VERSI TERTENTU


        setContent {
            MyDaraSiswaTheme {
                // Gunakan Surface sebagai dasar agar lebih stabil
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        DataSiswaApp(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}