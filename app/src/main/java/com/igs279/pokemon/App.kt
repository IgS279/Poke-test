package com.igs279.pokemon

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.igs279.pokemon.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "App onCreate +")

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    searchViewModelModule,
                    randomViewModelModule,
                    favViewModelModule,
                    repoModule,
                    databaseModule)
            )
        }
    }
}


