package com.example.tmdbapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TMDBApp : Application() {
    override fun onCreate() {
        super.onCreate()
        timber.log.Timber.plant(timber.log.Timber.DebugTree())
    }
}