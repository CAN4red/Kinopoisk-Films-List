package com.example.fintechlab2023

import android.app.Application
import com.example.fintechlab2023.data.AppContainer
import com.example.fintechlab2023.data.DefaultAppContainer

class FilmsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
