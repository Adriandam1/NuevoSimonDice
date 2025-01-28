package com.example.nuevosimondice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Creamos un objeto de la clase Myviewmodel y los pasamos a la composable "myApp"
        val viewModel = MyViewModel()
        enableEdgeToEdge()
        setContent {
            myApp(viewModel)
        }
    }
}